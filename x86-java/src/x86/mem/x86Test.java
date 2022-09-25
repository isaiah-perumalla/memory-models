package x86.mem;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class x86Test {

    public static final Unsafe UNSAFE;
    static {
        Unsafe unsafe = null;
        try {
            unsafe = Unsafe.getUnsafe();
        } catch (final Exception ex) {
            try {
                final Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);

                unsafe = (Unsafe) f.get(null);
            } catch (final Exception ex2) {
                throw new RuntimeException(ex2);
            }
        }
        UNSAFE = unsafe;
    }


    static   long thread0Val, thread1Val = 0;
    static   long r0, r1;
    static volatile long dummy;
    static void thread0()
    {
        thread0Val = 1;
        UNSAFE.fullFence();
        r1 = thread1Val;

    }

    static void thread1 ()
    {
        thread1Val = 1;
        r0 = thread0Val;
    }

    public static void main(String[] args) throws InterruptedException {

        long count = 0;
        while (true) {
            Thread t0 = new Thread(x86Test::thread0);
            Thread t1 = new Thread(x86Test::thread1);
            thread0Val = thread1Val = 0;
            t0.start();
            t1.start();

            t0.join();
            t1.join();
            count++;

            if (r0 == 0 && r1 == 0) {
                System.out.printf("(r0=%d, r1=%d, after %d runs)\n", r0, r1, count);
                break;
            }

        }

    }
}
