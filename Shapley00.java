import java.util.*;

public class Shapley00 {
	
	static int[][] myarr = new int[100][10];
	
	static void permute(int[] arr, int k) {
		int temp;
		for(int i = k; i < arr.length; i++) {
			temp = arr[i];
			arr[i] = arr[k];
			arr[k] = temp;
			permute(arr, k+1);
			temp = arr[k];
			arr[k] = arr[i];
			arr[i] = temp;
		}
		if (k == arr.length - 1) {
			//System.out.println(java.util.Arrays.toString(arr));
			myarr[k] = arr;
		}
	}
	
	static int fact(int x) { // rough and dirty
		if(x<=0 || x>100) return -1;
		if(x>1) return x*fact(x-1);
		else return 1;
	}

	// Shapley power index for 2-gloves game
	public static void main(String[] args) {
		
		String inputstr = "012";
		String[] inputmap = inputstr.split("");
		int t;
		int[] rho;
		int clen = inputmap.length;
		int[] carr = new int[clen];
		for (int i = 0; i < clen; i++) {
			carr[i] = i;
		}
		
		//System.out.println("clen: " + clen);
		permute(carr, 0);
		
		String output = "";
		String valstr = "";
		String valstri = "";
		int mc = 0, mci = 0, linetot = 0; // marginal costs and player contribution
		int lastplayer = myarr[0][0];
		int plrtot = 0;
		int fclen = fact(clen);
		boolean sumonly = true;
		System.out.println("fclen: " + fclen);
		
		for(int i=0; i<fclen; i++) {
			output = "" + myarr[i];
			for(int j=0; j<(clen+1); j++) {
				rho = Arrays.copyOfRange(myarr[i], 0, j);
				output += " " + rho;
				valstr = "";
				valstri = "";
				for(int k=0; k<j; k++) {
					System.out.println("rho: " + rho[k]);
					valstr += inputmap[rho[k]-1]; // v(rho) value of current coalition
					if(k>0) valstri += inputmap[rho[k]-1]; // v(rho / {i}) value of coalition less first player
				}
				output += ": " + valstr;
				
				// str1.toLowerCase().contains(str2.toLowerCase())
				
				if(valstr.toLowerCase().contains("l") && valstr.toLowerCase().contains("r")) { // value of current coalition
					mc = 1;
				}
				if(valstri.toLowerCase().contains("l") && valstri.toLowerCase().contains("r")) { // value of coalition less first player
					mci = 1;
				}
				linetot += mc - mci; // add to player
				mc = 0;
				mci = 0;
			}
			output += " v: " + linetot;
			plrtot += linetot;
			if(lastplayer != myarr[Math.min((i+1), (fclen-1))][0] || i == (fclen-1)) {
				lastplayer = myarr[Math.min((i+1), (fclen-1))][0];
				output += " plr total: " + plrtot;
				plrtot = 0;
				if(sumonly) {
					output = "";
				}
			}
			mc = 0;
			mci = 0;
			linetot = 0;
			if(!sumonly) {

			}
			output = "";
		}
	}	
}