# RareRouter
简介：该路由框架主要用于解决android组件化不同Module之间方法调用、activity启动等。
     该代码仓库中，main分支为demo示例，用来参考如何使用，SourceCode分支为jar包源码。

原理：不同module之间，将需要相互调用的方法加上相应的注解。编译时，该框架通过识别注解，同时自动生成一些java文件，
     这些java文件将调用和被调用方关联起来。运行时，调用方通过该框架去调用被调用方的实现方法。

特点：1、使用简单，只需maven依赖、增加一个配置文件RareRouter.xml。
     2、App运行时，不需要初始化，直接使用，不影响启动时间。
     3、App运行时，路由表的生成0反射，避免大量反射引起性能问题。
     4、App运行时，路由表存在于方法区域，不会在堆区占用内存。
     5、使用方式类似于Spring、retrofit框架。
     6、module之间方法调用，不需要使用公共common-module或者base-module做胶水连接。
     7、module之间方法调用，参数、返回值支持基本数据类型、复杂数据类型(需要实现RouterParcelable接口)，复杂数据类型自动转换。
     8、module之间方法调用，支持同步、异步、回调。


使用方法：
     1、maven依赖方式。
        参与编译的module中添加：
          implementation 'com.lxf:RareRouter:2.0'
          annotationProcessor 'com.lxf:RareRouter:2.0'
        项目添加maven地址：
          maven { url 'https://raw.githubusercontent.com/996coding/RouterMaven/main'}
     2、jar包依赖方式。
        参与编译module的libs目录中增加该jar包，同时build.gradle中添加：
          implementation files('libs/RareRouter.jar')
          annotationProcessor files('libs/RareRouter.jar')
     3、工程源码依赖。
        参考代码仓库的SourceCode分支。

代码示例：
     假设 组件化模块A 调用 模块B 的代码。
     Module A中代码：
          public interface HelloWorld {
                    @RouterMethod(path = "say_hello_world")
                    String say(String content);
          }
          HelloWorld impl = RareApplication.createImpl(HelloWorld.class);
          String res = impl.say("Hello World!");

     Module B中代码(ModuleB也可以是热加载插件)：
          public class HelloWorldImpl {
                    @RouterMethod(path = "say_hello_world")
                    public String say(String content) {
                         return content + "->HelloWorldImpl";
                    }
          }



