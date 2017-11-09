/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rainara_hw3_2;

/**
 *
 * @author Rainara
 */
public class PrimeThread extends Thread {

	private static int JOBSLIDE = 10000;
	private long nTime;
	private int pCount;
	private static int limit;
	private static int finishWork = 0;
	private static Object lock = new Object();
	PrimeThread[] threadList = new PrimeThread[16];

	public PrimeThread(String maxNumber, String s_threadCount) {

		try {
			limit = Integer.parseInt(maxNumber);
			int threadCount = Integer.parseInt(s_threadCount);

			if (threadCount == 0)
				throw new Exception("The thread number cannot be 0");

			if (threadCount > 16) {
				threadCount = 16;
				System.out
						.println("The thread number cannot greater than 16, and set to 16");
			}

			JOBSLIDE = limit / threadCount / 20;
			if (JOBSLIDE < 1000)
				JOBSLIDE = 1000;
			else if (JOBSLIDE > 20000)
				JOBSLIDE = 20000;

			for (int i = 1; i <= threadCount; i++) {

				threadList[i - 1] = new PrimeThread();
				threadList[i - 1].start();

			}

			for (int i = 1; i <= threadCount; i++) {

				threadList[i - 1].join();
				System.out.printf("%nTHREAD-" + i + ": Prime Count = %d",
						threadList[i - 1].getPCount());
				System.out.printf("# Seconds Used = %6.2f",
						threadList[i - 1].getSTime());
			}

		} catch (Exception ex) {
			System.out.println(ex);

		}

	}

	public PrimeThread() {
	}

	@Override
	public void run() {

		long start = System.nanoTime();
		while (finishWork < limit) {
			JobSlide jobSlide = null;
			synchronized (lock) {
				if (finishWork < limit) {
					jobSlide = getWork();
					if (jobSlide.getEnd() > limit)
						jobSlide.setEnd(limit);
					finishWork = jobSlide.getEnd();

				}

			}

			if (jobSlide == null)
				break;

//			System.out.println(Thread.currentThread().getName() + "   "
//					+ jobSlide.getInit() + "   " + jobSlide.getEnd());

			for (int num = jobSlide.getInit(); num <= jobSlide.getEnd(); num += 2) {
				if (isPrime(num)) {
					pCount++;
				}
			}

		}

		long end = System.nanoTime();
		nTime = end - start;
	}

	public boolean isPrime(int n) {

		if (n % 2 == 0)
			return false;

		int limit = (int) Math.ceil(Math.sqrt((double) n));

		for (int divisor = 3; divisor <= limit; divisor += 2)
			if (n % divisor == 0)
				return false;

		return true;
	}

	public JobSlide getWork() {

		JobSlide jobSlide = new JobSlide(finishWork + 1, finishWork + JOBSLIDE);
		return jobSlide;
	}

	public int getPCount() {
		return pCount;
	}

	public long getNTime() {
		return nTime;
	}

	public double getSTime() {
		return nTime / 1000000000.0;
	}

}

class JobSlide {

	public int getInit() {
		return init;
	}

	public void setEnd(int limit) {
		// TODO Auto-generated method stub
		end = limit;

	}

	public int getEnd() {
		return end;
	}

	private int init;
	private int end;

	public JobSlide(int init, int end) {
		this.init = init;
		this.end = end;
	}
}
