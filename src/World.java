public class World extends Thread{

    //override run method from thread class

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("World");
            System.out.println(Thread.currentThread().getName());
        }
    }
}
