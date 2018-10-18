package com.nndims.disaster.product.comm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoubleKeepTwo {

	//7.3745054208202E11
	//7.3745054218E8
	
	public static void main(String[] args) {

		//System.out.println(Double.MAX_VALUE);
		//System.out.println(keepTwoBit("7.374505421820244E8"));
		//System.out.println(getTwoDecimal(7.3745054208202E11));

		//System.out.println(Double.MAX_VALUE);
		System.out.println(keepTwoBit("-3.5703674492E7"));
		/*System.out.println(getTwoDecimal(7.3745054208202E11));

		
		double a1= 1313/7799201.0;
        //System.out.println(a1);//科学计数法
        BigDecimal bg = new BigDecimal(a1);
        //System.out.println(bg);//普通的计数法
        
        Object c1 = 1079366.6399999992;
        double cui = (Double)c1/100000;
        System.out.println(cui);
*/
	}
	
	/**非科学记数法double保留两位小数
	 * 
	 * @param num
	 * @return
	 */
    public static double getTwoDecimal(double num) {  
    	
    	BigDecimal bd = new BigDecimal(num);
    	//double zxjeF = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    	
    	DecimalFormat dFormat=new DecimalFormat("0.0");          
    	String yearString=dFormat.format(bd);          
    	Double temp= Double.valueOf(yearString);   
    	
    	return temp;     
    }
    
    /**处理科学记数法double
     * 
     * @param zxje
     * @return
     */
    public static String keepTwoBit(String zxje){

    	BigDecimal bd = new BigDecimal(zxje);
    	double zxjeF = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    	
    	//为了避免保留两位小数后出现，0.0的情况。
    	//if(zxjeF<0.005){
    		if(zxjeF<0.01){
        		return 0.01+"";
    		}
    	//}
    	
    	DecimalFormat df = new DecimalFormat("0.0");
    	zxje = df.format(zxjeF)+"";
    	//zxje = df.format(zxjeF);
    	
    	return zxje;
    }

    
    public static String formatDouble2(double d) {

        
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        
        NumberFormat n= NumberFormat.getInstance();
        n.setGroupingUsed(false);
        
//        return String.valueOf(bg.doubleValue());
        
        return n.format(bg);
    }

    

}
