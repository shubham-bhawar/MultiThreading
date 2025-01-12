//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //extend thread
        World world = new World();  //NEW State
        world.start(); //Runnable state waiting for CPU
        System.out.println("Hello world");
        //runnable -> Implement runnable
        RunnableWorld rn = new RunnableWorld();
        Thread thread = new Thread(rn);
        thread.start();

        System.out.println(Thread.currentThread().getName());

    }
}