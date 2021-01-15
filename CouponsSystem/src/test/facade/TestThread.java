package test.facade;

import main.core.db.ConnectionPool;
import main.core.exceptions.ConnectionPoolException;

public class TestThread extends Thread {

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			ConnectionPool.getInstance().closeAllConnections();
		} catch (ConnectionPoolException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.run();
	}
	

}
