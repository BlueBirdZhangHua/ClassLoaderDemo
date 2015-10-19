
public class MainClass {

	public static void main(String[] args) {
		testOne();
		try {
			testTwo();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void testOne(){
		MainClass mainClass = new MainClass();
		Class class1 = mainClass.getClass();
		ClassLoader classLoader = class1.getClassLoader();
		//运行一个程序时，总是由AppClass Loader（系统类加载器）开始加载指定的类。
		//在加载类时，每个类加载器会将加载任务上交给其父，如果其父找不到，再由自己去加载。
		//Bootstrap Loader（启动类加载器）是最顶级的类加载器了，其父加载器为null.
		
		System.out.println(classLoader);//AppClass Loader AppClass Loader（系统类加载器AppClassLoader）：加载System.getProperty("java.class.path")所指定的路径或jar。在使用Java运行程序时，也可以加上-cp来覆盖原有的Classpath设置，例如： java -cp ./lavasoft/classes HelloWorld
		System.out.println(classLoader.getParent());//Extended Loader（标准扩展类加载器ExtClassLoader）：加载System.getProperty("java.ext.dirs")所指定的路径或jar。在使用Java运行程序时，也可以指定其搜索路径，例如：java -Djava.ext.dirs=d:\projects\testproj\classes HelloWorld
		System.out.println(classLoader.getParent().getParent());//BootstrapLoader 由C语言实现因此这里获取为null  Bootstrap Loader（启动类加载器）：加载System.getProperty("sun.boot.class.path")所指定的路径或jar。
	}

	//	类加载有三种方式：
	//	1、命令行启动应用时候由JVM初始化加载
	//	2、通过Class.forName()方法动态加载
	//	3、通过ClassLoader.loadClass()方法动态加载
	public static void testTwo() throws ClassNotFoundException{
		System.out.println("=============TestTwo==========");
		ClassLoader classLoader = MainClass.class.getClassLoader();
		System.out.println(classLoader);
		//使用ClassLoader的loadClass加载Class 不会执行静态初始化代码块
		System.out.println("Load by ClassLoader");
		classLoader.loadClass("TestClass");
		//使用Class.forName加载Class默认执行，静态初始化代码块
		System.out.println("Load by Class.forName");
		Class.forName("TestClass");
		//使用CLass.forName加载Class，并设置对应的ClassLoader，第二个参数决定会不会执行静态初始化代码块
		System.out.println("Load by Class.forName");
		Class.forName("TestClass", false, classLoader);
		Class.forName("TestClass", true, classLoader);
		//指定的Class只注册在AppClassLoader上。ExtenedClassLoader只调用自己和父加载器的loadClass所以会抛出异常
		classLoader.getParent().loadClass("TestClass");
	}

	
}
