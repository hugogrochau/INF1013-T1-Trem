import java.util.*;

public class Main {

	public static void main(String[] args) {
		Linha l1=new Linha("1 - ");
		Linha l2=new Linha("2 - ");
		String s;
		
		new Thread(l1).start();
		new Thread(l2).start();
		
		Scanner sc=new Scanner(System.in);
		
		System.out.printf("Mensagem?\n");
		s=sc.nextLine();
		while(s.compareTo("#")!=0) {
			System.out.printf("Qual thread?\n");
			int linha=sc.nextInt();
			sc.nextLine();
			if(linha==1)
				l1.setMsg(s);
			else
				l2.setMsg(s);
			System.out.printf("Mensagem?\n");
			s=sc.nextLine();			
		}
		sc.close();
	}
}