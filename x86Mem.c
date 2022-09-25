#include <pthread.h>
#include <stdio.h>
 
int x, y = 0;
int r0, r1;
 
void *core0 (void *arg)
{
  x = 1;
  asm volatile ("" ::: "memory"); // ensure GCC compiler will not reorder 
  r1 = y;
  return 0;
}
 
void *core1 (void *arg)
{
  y = 1;
  asm volatile ("" ::: "memory"); // ensure GCC compiler will not reorder 
  r0 = x;
  return 0;
}
 

int main (void)
{
  pthread_t thread0, thread1;
  while (1) {
    x = y = 0;
    //Start threads
    pthread_create (&thread0, NULL, core0, NULL);
    pthread_create (&thread1, NULL, core1, NULL);

    //wait for threads to complete
    pthread_join (thread0, NULL);
    pthread_join (thread1, NULL);
 
    if (r0 == 0 && r1 ==0) {
      printf ("(r0=%d, r1=%d)\n", r0, r1);
      break;
    }

  }
  return 0;
}