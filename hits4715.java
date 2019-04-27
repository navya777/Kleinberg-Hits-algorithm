
/* Navya Martin Kollapally CS610 PrP*/



import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * @author navya
 *
 */
public class hits4715
{
	private static List<ArrayList<Integer>> adjacencyList=new ArrayList<ArrayList<Integer>>();
	private static double[] a;
	private static double[] h;
	private static double[] a0;
	private static double[] h0;
	private static int n;
	private static int m;
	private static int noOfIterations;
	private static double error=0;
	
	private static String inpt;
	
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		// TODO Auto-generated method stub
		noOfIterations=Integer.parseInt(args[0]);
		int initalValue=Integer.parseInt(args[1]);
	    String inputname=args[2];
		int flag=0;
		FileInputStream filestream=new FileInputStream(inputname);
		BufferedReader input= new BufferedReader(new InputStreamReader(filestream));
		while (input.ready())
		{
			inpt=input.readLine();
			String[] integersInString=inpt.split(" ");
			int inputarray[]=new int[integersInString.length];
			if(flag==0)
			{
				for(int i=0;i<1;i++)
				{
					inputarray[i]=Integer.parseInt(integersInString[i]);
					n=inputarray[i];
					m=inputarray[i++];
					if(n>10)
					{
						noOfIterations=0;
						initalValue=-1;
					}
					//Initialize adjacency list and variables
					valueAddtoVariables(initalValue);
					//Calculate the error depending on  number of iteration
					error=errorateCalculate();
				}
				
			}
			if (flag!=0)
			{
				int v1=Integer.parseInt(integersInString[0]);
				int v2=Integer.parseInt(integersInString[1]);
				adjacencyList.get(v1).add(v2);
				
			}
			++flag;
			
			
		}
		int count=0;
		printAuthorityandHub(count);
		do
		 {
			 calculateAuthorityValue();
			 calculateHubValue();
			 scalingAuthorityFactort();
			 scalingHubFactort();
			 ++count;
			 if(n<=10)
			 {
				 printAuthorityandHub(count);
			 }
			 
			 
		 }while(!convergingIteration(count));
		if(n>10)
		{
			printAuthorityandHubLarge(count);
		}
		input.close();
	}
	private static double errorateCalculate() 
	{
		// TODO Auto-generated method stub
		if(noOfIterations>=0)
		{
			return 0.00001;
		}
		
		else if(noOfIterations<0)
				{
					error=Math.pow(10, noOfIterations);
				}
		
		
		return error;
	}
	private static void valueAddtoVariables(int initalValue) 
	{
		// TODO Auto-generated method stub
		adjacencyList=new ArrayList<ArrayList<Integer>>(n);
		a=new double[n];
		h=new double[n];
		a0=new double[n];
		h0=new double[n];
		
		double initialise;
		switch(initalValue)
		{
		case 0:
			initialise=0;
			break;
		case 1:
			initialise=1;
			break;
		case -1:
			initialise=1/(double)n;
			break;
		case -2:
			initialise=1/Math.sqrt(n);
			break;
	     default:
	    	 initialise=initalValue;
	    	 break;
			
		}
		for(int i=0;i<n;i++)
		{
			adjacencyList.add(new ArrayList<>());
			h[i]=initialise;
			a[i]=initialise;
		}
	}
	
	private static void printAuthorityandHub(int noOfIterations ) 
	{
		// TODO Auto-generated method stub
		if(noOfIterations==0)
		{
			System.out.print("Base : "+noOfIterations+":");
		}
		else
		{
			
			System.out.print("Iteration :"+noOfIterations+":");
		}
		DecimalFormat df=new DecimalFormat("0.0000000");
		for(int i=0;i<n;i++)
		{
			System.out.print("A/H[" +i+ "]="+df.format(Math.floor(a[i]*1e7)/1e7)+ " / "+df.format(Math.floor(h[i]*1e7)/1e7) + " ");
			
		}
		System.out.println();
		
	}
	private static boolean convergingIteration(int count) 
	{
		// TODO Auto-generated method stub
		if(count==noOfIterations)
		{
			return true;
		}
		else 
		{
	
		for(int i=0;i<n;i++)
				{
					if((Math.abs(a0[i]- a[i])>error)||(Math.abs(h0[i]- h[i])>error))
					{
						return false;
					}
			
				}
				
				return true;
		}
	}
		
	
	private static void calculateAuthorityValue()
	{  double sum;
		// TODO Auto-generated method stub
		for(int i=0;i<a.length;i++)
		{
			a0[i]=a[i];
			sum=0.0;
			for(int j=0;j<h.length;j++)
			{
				if(adjacencyList.get(j).contains(i))
				{
					sum=sum+h[j];
				}
			}
			a[i]=sum;
		}
		
	}
	private static void calculateHubValue() 
	{
		// TODO Auto-generated method stub
		double sum;
		for(int i=0;i<h.length;i++)
		{
			 h0[i]=h[i];
			 sum=0.0;
			for(int j=0;j<a.length;j++)
			{
				if(adjacencyList.get(i).contains(j))
				{
					sum=sum+a[j];
				}
				h[i]=sum;
			}
			
		}
		
	}
	private static void scalingAuthorityFactort() 
	{
		// TODO Auto-generated method stub
		double scale=0;
		for(int i=0;i<a.length;i++)
		{
			scale+=Math.pow(a[i],2);
		}
		scale=Math.sqrt(scale);
		for(int j=0;j<a.length;j++)
		{
			a[j]=a[j]/scale;
		}
	}
	private static void scalingHubFactort() 
	{
		// TODO Auto-generated method stub
		double scale=0;
		for(int i=0;i<h.length;i++)
		{
			scale+=Math.pow(h[i],2);
		}
		scale=Math.sqrt(scale);
		for(int j=0;j<n;j++)
		{
			h[j]=h[j]/scale;
		}
	}
	private static void printAuthorityandHubLarge(int noOfIterations) 
	{
		// TODO Auto-generated method stub
		System.out.print("Iteration :"+noOfIterations+":");
		DecimalFormat df= new DecimalFormat("0.0000000");
		for(int i=0;i<n;i++)
		{
			System.out.print("A/H[" +i+ "]="+df.format(Math.floor(a[i]*1e7)/1e7)+ " / "+df.format(Math.floor(h[i]*1e7)/1e7) + " ");
			
		}
		System.out.println();
		
	}
	
	

	
	
}
			




















