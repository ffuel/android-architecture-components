# Android architecture components.

[![build status](http://gitlab.65apps.com/65AppsAndroid/architecture-components/badges/master/build.svg)](http://gitlab.65apps.com/65AppsAndroid/architecture-components/commits/master)

___
## Intro
Репозиторий представляет из себя набор gradle модулей, содержащих набор архитектурных компонентов и приложение - пример.

- *app* - приложение - пример использования архитектурных компонентов.
- *architecturecomponents* - базовый модуль, все остальные модули зависят от него.
- *ciceronearchitecturecomponents* - модуль реализующий интерфейс NavigatorDelegate и Router из базового модуля, используя библиотеку Cicerone.
- *daggerarchitecturecomponents* - модуль содержит реализацию DI на основе библиотеки Dagger 2 и предоставляет базовые Dagger модули.
- *moxyarchitecturecomponents* - модуль содержит реализацию паттерна MVP из базового модуля на основе библиотеки Moxy.
- *moxydaggerarchitecturecomponents* - модуль содержит реализацию паттерна MVP из базового модуля на основе модуля *moxyarchitecturecomponents* и *daggerarchitecturecomponents*, т.е. Moxy + Dagger.
- *compiler* - модуль содержит AnnotationProcessor для обработки аннотации @ContributesPresenterInjector и саму аннотацию @ContributesPresenterInjector. Необходимо подключать его таким образом:
```
compileOnly project(':compiler')
annotationProcessor project(':compiler')
```
Пример можно посмотреть в *app* модуле.

___

## Работа с Android architecture components

Для начала, необходимо подключить этот репозиторий к вашему проекту как git submodule [![git submodule](https://git-scm.com/images/logo.png)](https://git-scm.com/book/ru/v1/%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D1%8B-Git-%D0%9F%D0%BE%D0%B4%D0%BC%D0%BE%D0%B4%D1%83%D0%BB%D0%B8)
и добавить необходимые gradle модули в проект. Если вы не планируете использовать Moxy, Dagger, Cicerone, то достаточно подключить только *architecturecomponents*, 
но придется тогда написать свои реализации для MVP, DI и навигации соответственно.

### Пример минимального использования

ApplicationModule
```java
@Module(includes = {
        AndroidInjectionModule.class, 	// Необходимый модуль для работы Dagger AndroidInjection 
        PresenterInjectionModule.class, // Необходимый модуль для работы Dagger PresenterInjection
        SchedulersModule.class,			// Модуль, предоставляющий зависимости для RxSchedulers
        LoggerModule.class,				// Модуль, предоставляющий реализацию лога на основе AndroidLog
        ResourcesModule.class,			// Модуль, предоставляющий реализацию StringResources на основе Context.getString()
        ConnectionStateModule.class,	// Модуль, предоставляющий реализацию ConnectionReceiverSource на основе ConnectionBroadcastReceiverSource
        PermissionsModule.class,		// Модуль, предоставляющий реализацию PermissionsSource и требует инициализации через конструктор параметром PermissionsManager
        PresenterBuilder.class,		    // Предоставляет subcomponent'ы презентеров с помощью аннотации @ContributesPresenterInjector
        ActivityBuilder.class,			// Предоставляет Activity и Fragment subcomponent'ы с помощью аннотации @ContributesAndroidInjector
        MainNavigationModule.class,		// Должен предоставлять реализации Router и, опционально, NavigatorHolder(если вы используете Cicerone)
        AppDataModule.class 			// Должен предоставлять зависимости для вашего data-слоя приложения
})
public class ApplicationModule {

    @Provides
    @NonNull
    Context providesContext(@NonNull SampleApplication application) {
        return application;
    }
}
```

ApplicationComponent
```java
@Component(modules = {
        ApplicationModule.class
})
@Singleton
public interface ApplicationComponent extends AndroidInjector<SampleApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleApplication> {
        @NonNull
        public abstract Builder permissionsModule(@NonNull PermissionsModule module); // Предоставляем зависимость от PermissionsModule
    }
}
```

Application
```java
public class SampleApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends dagger.android.DaggerApplication> applicationInjector() {
        return com.a65apps.architecturecomponents.DaggerSampleComponent.builder()
                .permissionsModule(new PermissionsModule(new PermissionsManager())) // Инициализируем PermissionsModule
                .create(this);
    }
}
```

MainNavigationModule
```java
@Module
public class MainNavigationModule {

    private final Cicerone<CiceroneRouter> router = Cicerone.create(new CiceroneRouter());

    @Provides
    @Singleton
    @NonNull
    Router providesRouter() {
        return router.getRouter();
    }

    @Provides
    @Singleton
    @NonNull
    NavigatorHolder providesNavigatorHolder() {
        return router.getNavigatorHolder();
    }
}
```

Пишем главный модуль приложения:

```java
@Module
public class MainActivityModule extends DaggerActivityModule<MainState, MainParcelable,
        MainStateMapper, MainParcelMapper> {

    @Provides
    @NonNull
    FragmentActivity providesFragmentActivity(@NonNull MainActivity activity) {
        return activity;
    }

    @Provides
    @NonNull
    ActivityModule<MainState, MainParcelable, MainStateMapper,
                MainParcelMapper> providesModule(@NonNull FragmentActivity activity,
                                                 @NonNull NavigatorHolder holder) {
        return new ActivityModule<MainState, MainParcelable, MainStateMapper,
                MainParcelMapper>() {

            @NonNull
            @Override
            public MainStateMapper provideStateToParcelableMapper() {
                return new MainStateMapper();
            }

            @NonNull
            @Override
            public MainParcelMapper provideParcelableToStateMapper() {
                return new MainParcelMapper();
            }

            @NonNull
            @Override
            public NavigatorDelegate provideNavigatorDelegate() {
                return new CiceroneDelegate(holder, new MainNavigator(activity, new MainContainerIdProvider()));
            }
        };
    }
}
```

в ActivityBuilder модуль добавляем:
```java
@Module
public interface ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class, FragmentBuilder.class}) // FragmentBuilder предоставляет subcomponent'ы для Fragment'ов
    @NonNull
    MainActivity bindMainActivity();
}
```

Создаем компонент для презентера главного модуля:
```java
@Module
public interface MainPresenterComponent {

    @ContributesPresenterInjector(modules = {MainPresenterModule.class, ChildPresenterComponent.class}) // ChildPresenterComponent предоставляет subcomponent'ы для презентеров фрагметов в @ChildPresenterScope
    MainPresenter bindMainPresenter();
}
```
Создаем компонент для презентеров фрагментов главного модуля:
```java
@Module
public interface ChildPresenterComponent {

    @ContributesPresenterInjector(modules = SamplePresenterModule.class, isChild = true) // Сообщаем AnnotationProcessor'у что нужно генерировать код компонента в @ChildPresenterScope
    SamplePresenter bindSamplePresenter();

    ... // Другие сабкомпоненты главного модуля
}
```
Создаем модуль для презентера главного модуля:
```java
@Module(includes = MainDomainModule.class) // MainDomainModule - предоставляет зависимости бизнес-логики для презентера главного модуля
public interface MainPresenterModule {
}
```

Создаем модуль для презентера фрагмента главного модуля:
```java
@Module(includes = SampleDomainModule.class) // SampleDomainModule - предоставляет зависимости бизнес-логики презентера фрагмента главного модуля
public interface SamplePresenterModule {
}
```

Регистрируем компонент презентера главного модуля в PresenterBuilder:

```java
@Module(includes = {
        com.a65apps.architecturecomponents.sample.presentation.main.MainPresenterComponent_BindMainPresenter.class
})
public interface PresenterBuilder {
}
```
Обратите внимание на полное имя класса модуля и название класса. AnnotationProcessor сгенерирует на основе модуля MainPresenterComponent модуль MainPresenterComponent_BindMainPresenter.
Название сгенерированного класса строится по такому паттерну: ```название класса модуля + _Bind + Название презентера```.

MainDomainModule
```java
@Module
public interface MainDomainModule {

    @Binds
    @PresenterScope // Обязательно указываем, что MainModel будет жить в @PresenterScope
    @NonNull
    MainInteractor bindsTo(@NonNull MainModel model);
}
```

С DI главного модуля закончено, пишем реализацию:

MainActivity
```java
public class MainActivity extends MoxyDaggerActivity<MainState, MainParcelable, MainPresenter> 
    implements MainView, HasPresenterSubComponentBuilders {

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void updateState(@NonNull MainParcelable state) { // В этот метод всегда приходит текущее состояние экрана.
        // Обновляем состояние экрана здесь
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected MainPresenter getPresenter() {
        return presenter;
    }

    @ProvidePresenter
    @NonNull
    MainPresenter provideMainPresenter() {
        return PresenterInjector.build(MainPresenter.class, this); // Используем PresenterInjector для предоставления MainPresenter
    }

    @NonNull
    @Override
    public PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return presenter.getPresenterSubComponentBuilder(presenterClass);
    }

    ... // Другие методы
}
```

MainPresenter
```java
@InjectViewState
public class MainPresenter extends MoxyPresenter<MainState, MainView, MainInteractor, Router> {

    @NonNull
    private final PresenterSubComponentBuilderFactory componentFactory; // Предоставляем фабрику для сабкомпонентов главного модуля

    @Inject
    MainPresenter(@NonNull ExecutorsFactory executors, @NonNull MainInteractor interactor,
                  @NonNull ApplicationLogger logger,
                  @NonNull PresenterSubComponentBuilderFactory componentFactory) {
        super(executors, interactor, logger);
        this.componentFactory = componentFactory;
    }

    @NonNull
    PresenterComponentBuilder getPresenterSubComponentBuilder(
            @NonNull Class<? extends Presenter> presenterClass) {
        return componentFactory.get(presenterClass);
    }

    ... // Другие методы
}
```

MainState - состояние экрана
```java
@AutoValue
public abstract class MainState implements State {

    public static MainState create(Параметры состояния) {
        return new AutoValue_MainState(Параметры состояния);
    }

    ... // Поля состояния
}
```

MainParcelable - состояние экрана, сереализуемое в Bundle во время onSaveInstanceState()
```java
@AutoValue
public abstract class MainParcelable implements Parcelable {

    public static MainParcelable create(Параметры состояния) {
        return new AutoValue_MainParcelable(Параметры состояния);
    }

    ... // Поля состояния
}
```

MainStateMapper - Мэппер из MainState в MainParcelable
```java
public class MainStateMapper extends StateToParcelableMapper<MainState, MainParcelable> {

    @Inject
    public MainStateMapper() {
        super();
    }

    @NonNull
    @Override
    public MainParcelable map(@NonNull MainState mainState) {
        ... // Реализация
    }
}
```

MainParcelMapper - Мэппер из MainParcelable в MainState
```java
public class MainParcelMapper extends ParcelableToStateMapper<MainParcelable, MainState> {

    @Inject
    public MainParcelMapper() {
        super();
    }

    @NonNull
    @Override
    public MainState map(@NonNull MainParcelable mainParcelable) {
        ... // Реализация
    }
}
```

MainInteractor - Интерфейс взаимодействия презентера с модэлью главного экрана
```java
public interface MainInteractor extends Interactor<MainState, Router> {

    ... // Методы взаимодействия
}
```

MainModel - модель главного экрана
```java
final class MainModel extends BaseModel<MainState, Router> implements MainInteractor {

	// Зависимости

    @Inject
    MainModel(@NonNull Router router, Другие зависимости) {
        super(MainState.create(...), router);
        ... // Инициализация зависимостей
    }

    @UiThread
    @Override
    public void firstStart(boolean isRestoring) {
        // Какая-то логика при первоначальном старте экрана. Можно проверить флаг isRestoring для определения - восстанавливаемся ли мы из savedInstanceState
    }

    ... // Реализация методов взаимодействия
}
```

MainNavigator - реализация навигатора Cicerone
```java
public final class MainNavigator extends SupportAppNavigator {

    MainNavigator(@NonNull FragmentActivity activity, @NonNull ContainerIdProvider idProvider) {
        super(activity, idProvider.get());
    }

    @Override
    protected Intent createActivityIntent(Context context, String screenKey, Object data) {
    	// Здесь предоставляем Intent
    }

    @Override
    protected Fragment createFragment(@NonNull String screenKey, @Nullable Object data) {
        // Здесь предоставляем Fragment
    }
}
```

Далее уже можно приступить к написанию следующего модуля приложения на основе MoxyDaggerFragment
примеры можно посмотреть в *app* модуле.
