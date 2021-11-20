# RareRouter
简介：在安卓项目组件化开发中，不同module之间无法直接调用方法、无法直接启动activity，该路由框架主要用于解决这些问题。
     该代码仓库中，main分支为demo示例，用来参考如何使用，SourceCode分支为jar包源码。

原理：不同module之间，将需要相互调用的方法加上相应的注解。编译时，该框架通过识别注解，同时自动生成一些java文件，
     这些java文件将调用和被调用方关联起来。运行时，调用方通过该框架去调用被调用方的实现方法。

特点：1、使用简单，只需maven依赖即可。
     2、无需在Application中初始化，代码任何地方可以直接引用。 
     3、App运行时，路由表的生成0反射，避免大量反射引起性能问题。 
     4、正是由于第2、3点，该框架不影响App冷启动。
     5、module之间方法调用，不需要使用公共common-module或者base-module做胶水连接。
     6、module之间方法调用，参数、返回值支持使用基本数据类型和复杂数据类型，复杂数据类型自动转换。
     7、module之间方法调用，支持同步、异步、回调。
     8、当前module调用另一个module的方法，感觉就像在本module调用一样，无需类型强制转换。


使用方法：
     1、maven依赖方式。
        参与编译的module中添加：
          implementation 'com.lxf:RareRouter:1.0'
          annotationProcessor 'com.lxf:RareRouter:1.0'
        项目添加maven地址：
          maven { url 'https://raw.githubusercontent.com/996coding/RouterMaven/main'}
     2、jar包依赖方式。
        参与编译module的libs目录中增加该jar包，同时build.gradle中添加：
          implementation files('libs/RareRouter.jar')
          annotationProcessor files('libs/RareRouter.jar')
     3、工程源码依赖。
        参考代码仓库的SourceCode分支。

