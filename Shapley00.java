import java.util.*;

public class Shapley00 {
	
	static int iter = 0;
	
	static void permute(int[] arr, int k, int[][] rarr) { // permutations
		int temp;
		
		for(int i = k; i < arr.length; i++) {
			temp = arr[i];
			arr[i] = arr[k];
			arr[k] = temp;
			permute(arr, k+1, rarr);
			temp = arr[k];
			arr[k] = arr[i];
			arr[i] = temp;
		}
		if (k == arr.length - 1) {
			//rarr[iter] = arr;
			System.arraycopy(arr, 0, rarr[iter], 0, arr.length);
			//System.out.println("iter: " + iter + " : " + Arrays.toString(rarr[iter]));
			iter++;
		}
	}
	
	static int fact(int x) { // rough and dirty factorial
		if(x<=0 || x>100) return -1;
		if(x>1) return x*fact(x-1);
		else return 1;
	}

	// Shapley power index for 2-gloves game
	public static void main(String[] args) { // usage: java Shapley00 llr
		
		String inputstr = args[0]; // get string of glove distribution, e.g. llrrr
		String[] inputmap = inputstr.split(""); // convert string to array
		int t;
		int[] rho; // rho contains coalition up to current position in permutation
		int clen = inputmap.length; // length of input string = number of players
		int[] carr = new int[clen];
		for (int i = 0; i < clen; i++) {  // create player position array
			carr[i] = i;
		}
		
		int fclen = fact(clen); // number of permutations
		int[][] myarr = new int[fclen][clen]; // player position permutations
		permute(carr, 0, myarr); // all permutations of player positions are stored in myarr
		
		String output = "";
		String valstr = "";
		String valstri = "";
		int mc = 0, mci = 0, linetot = 0; // marginal costs and player contribution
		int lastplayer = myarr[0][0]; // actually the player at the first position, but used to check for last addition to coalition
		int plrtot = 0;
		boolean sumonly = true;

		
		for(int i=0; i<fclen; i++) { // all permutations of player positions
			output = "" + Arrays.toString(myarr[i]);
			for(int j=1; j<(clen+1); j++) { // all players at current permutation
				rho = Arrays.copyOfRange(myarr[i], 0, j); // coalition up to current position in permutation: [permut][playerpos]
				output += " " + Arrays.toString(rho);
				valstr = "";
				valstri = "";
				for(int k=0; k<j; k++) { // loop over current coalition
					valstr += inputmap[rho[k]]; // v(rho) value of current coalition
					if (k>0) valstri += inputmap[rho[k]]; // v(rho / {i}) value of coalition less first player
				}
				output += ": " + valstr;
				if(valstr.toLowerCase().contains("l") && valstr.toLowerCase().contains("r")) { // value of current coalition
					mc = 1; // marginal contribution
				}
				if(valstri.toLowerCase().contains("l") && valstri.toLowerCase().contains("r")) { // value of coalition less first player
					mci = 1; // marginal contribution
				}
				linetot += mc - mci; // add to player difference between margina contribution = value added
				mc = 0;
				mci = 0;
			}
			output += " v: " + linetot;
			plrtot += linetot;
			if(lastplayer != myarr[Math.min((i+1), (fclen-1))][0] || i==(fclen-1)) { // at the end of current player or end of permutations
				lastplayer = myarr[Math.min((i+1), (fclen-1))][0];
				output += " plr total: " + plrtot;
				plrtot = 0;
				if(sumonly) {
					System.out.println("output: " + output);
					output = "";
				}
			}
			mc = 0;
			mci = 0;
			linetot = 0;
			if(!sumonly) {
				System.out.println("output: " + output);
			}
			output = "";
		}
	}	
}