import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {


    private  int balance = 100;
    private  String name = "Shubham";


    //Reentrantlock is extrinsic lock ;

    private  final Lock lock = new ReentrantLock(true);

    private  final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();


    //synchronized -> intrinsic lock
    //problems with synchronized :- 1. no fairness , indefinite blocking , interruptibility , read write locking


    public synchronized void  withdraw(int amount){

        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        if(balance >= amount){
            System.out.println(Thread.currentThread().getName() + " proceeding with withdraw ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            balance-=amount;
            System.out.println(Thread.currentThread().getName() + " completed withdraw ");

        }else {
            System.out.println(Thread.currentThread().getName() + " Insufficient balance ");
        }

    }
    //Reentrantlock is extrinsic lock ;

    public void  withdrawExtrensic(int amount){


        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        try {
            // thread will wait till 100 ms if they can acquire lock
            if(lock.tryLock(100, TimeUnit.MILLISECONDS)){
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    // unlock lock so that other can access it
                    System.out.println(Thread.currentThread().getName() +  " lock released ");
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName() + " withdrawing amount");

            }else {
                System.out.println(Thread.currentThread().getName() + " unable to acquire lock ");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " unable to acquire lock ");
        }

    }

    public static void main(String[] args) {

        BankAccount account = new BankAccount();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               account.withdrawExtrensic(50);
            }
        };

        Thread t1 = new Thread(runnable,"T1 Thread ");

        Thread t2 = new Thread(runnable,"T2 Thread ");

        t2.start();
        t1.start();
    }

}
