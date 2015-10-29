
public class Linha implements Runnable {
	String msg="",id;
	
	public Linha(String i) {
		id=i;
	}
	
	public void setMsg(String m) {
		msg=m;
	}
	
	public void run() {
		while(true) {
			if(msg.compareTo("fim")==0)
				break;
			System.out.printf("%s  %s\n",id,msg);
					
			try {
				Thread.sleep(3000);
			}
			catch(InterruptedException e) {
			}
		}
	}
}