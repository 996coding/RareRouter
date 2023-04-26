# RareRouter

<a href="https://github.com/creekrouter/creekrouter_for_android">请移步至CreekRouter,本仓库不再维护</a>

## 简介：  
     该框架用于android组件化、插件化。解决组件化中不同module之间方法访问问题；解决插件化中访问插件方法问题。
     该代码仓库中，main分支为demo示例，SourceCode分支为框架源码。


## 基本使用：
### 一、配置文件。  
     项目根目录添加配置文件：RareRouter.xml。  
     配置文件内容如下：     
     <?xml version="1.0" encoding="UTF-8"?>  
     <!--    根节点至少包含一个AppModule属性,属性值为项目的ApplicationModule名称-->  
     <Rare AppModule="app">    
      <!--    指定log输出日志的目录，默认目录：rootDir/ApplicationModule/build-->  
      <!--    rootDir代表项目根目录路径，ApplicationModule代表主module名字-->  
      <Log dir="./app/build" />  
    </Rare>  
     
     
### 二、编译配置：
     application module需要依赖每个library module，library module之间可以不存在任何依赖。以下编译配置3选1即可。
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

### 三、代码示例：
     假设 组件化模块A 调用 模块B 的代码。
     
     Module A中代码：
          先定义一个接口：
          public interface HelloWorld_Visitor {
                    @RouterMethod(path = "say_hello_world")
                    String say(String content);
          }
          调用Module B的代码：
          HelloWorld_Visitor visitor = RareApplication.createImpl(HelloWorld_Visitor.class);
          String res = visitor.say("Hello World!");//该行代码调用到了Module B的代码

     Module B中代码(ModuleB也可以是热加载插件)：
          public class HelloWorld_Provider {
                    @RouterMethod(path = "say_hello_world")
                    public String say(String content) {
                         return content + "->HelloWorld_Provider";
                    }
          }
     运行结果：
     当Module A中执行 String res = visitor.say("Hello World!") 时，将会调用Module B的HelloWorld_Provider中
     的say方法，res将被赋值："Hello World!->HelloWorld_Provider"

     备注：插件化中，插件必须和RareRouter一起编译，宿主需要反射插件中一个固定类："com.lxf.genCode.ModuleRareImpl_插件名",
          该类是编译时候自动生成，无需手动编写，详细使用示例见demo源码中HttpJarActivity。




