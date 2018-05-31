# 精简的插件化fragment加载演示
本demo演示了如何加载以插件apk方式存在的fragment，完成了:
- 用尽可能少的代码实现插件fragment的代码和资源的加载
- 提供了生成插件框架所需的各种中间件的gradle代码

## 演示
![](./demo/demo.png)

## 如何运行
1. 下载该项目
2. 执行gradle sync以下载必要的依赖，完成后的工程结构:

![](./demo/project_structure.png)

3. 按顺序执行以下gradle task:

![](./demo/task_createPluginInterface.png)　　

![](./demo/task_createMainInterfaceJar.png)

![](./demo/task_createMainSupportV7InterfaceJar.png)
