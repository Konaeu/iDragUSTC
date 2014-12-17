import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//add 

public class iDragUSTC{
	//根据给定的TrafficLightTable和流量文件读入TrafficLightTable
	private static Map<String,List<String>> trafficLightTable = new HashMap<String,List<String>>();
	//红绿灯状态表,trafficLightStatus将保存所有时刻的状态表，currentLightStatus为当前时刻的红绿灯状态表
	private static Map<String,List<List<Integer>>> trafficLightStatus = new HashMap<String,List<List<Integer>>>();
	private static Map<String,List<Integer>> currentLightStatus = new HashMap<String,List<Integer>>();
	//车流量情况表
	private static Map<String,List<Integer>> trafficFlowTable = new HashMap<String,List<Integer>>();

	//T[i]时段所有路口车辆的 转向概率[左转，右转，直行]，初赛为定值
	private static double[] turnPro=new double[]{0.1,0.1,0.8};
	//T[i]时刻所有路口车辆的 通过率:[左转，右转，直行],初赛为定值
	private static Integer[] vehicleThroughRate=new Integer[]{2,2,16};

	public static void main(String[] args)throws IOException{
		System.out.println("iDragUSTC");
		loadTrafficLightTable("TrafficLightTable.txt");//加载trafficLight
		//System.out.println(trafficLight);
		loadTrafficFlowTable("..\\data\\flow0901.txt");
		initTrafficLightStatus();
 
		/*	
		*
		*对比trafficLightTable和trafficFlowTable检查读取数据是否存在错误
		*
		//观察保存结果是否正确
		List valueFlow=trafficFlowTable.get("tl16-tl15");
		for(int i=0;i<10;i++){
			System.out.print(" "+valueFlow.get(i));
		}
		Iterator iter1=trafficLightTable.entrySet().iterator();
		Iterator iter2=trafficFlowTable.entrySet().iterator();
		while(iter1.hasNext()){
			Map.Entry<String,List<String>>  entry1=(Map.Entry<String,List<String>>)iter1.next();
			String key=entry1.getKey();
			List value=new ArrayList();
			if(!trafficFlowTable.containsKey(key)){
				System.out.println(key);
			}else{
				 valueFlow=trafficFlowTable.get(key);
				System.out.print(key);
				for(int i=0;i<10;i++){
					System.out.print(" "+valueFlow.get(i));
				}
				System.out.println();
			}
			//System.out.println(key+"  "+value[0]);
			//value=trafficLightTable.get(key);
 
		}
		*/
		

	}
	 
	//从TrafficLightTable.txt 读入交通拓扑图 trafficLightTable
	public static void loadTrafficLightTable(String fileName) throws IOException{
		String trafficLight="";
		BufferedReader readerTxt= new BufferedReader(new FileReader(fileName));
		String lineStr="";
		//行扫描读入TrafficLight
		while(lineStr!=null){
			if(lineStr!=""){
				trafficLight+=lineStr+";";
			}
			lineStr=readerTxt.readLine();		
			//System.out.println(lineStr);	
		}
		//字符串型的trafficLight读入到HashMaP存储类型的trafficLightTable
		String[] splitStrS=trafficLight.split(";");
		for(String splitStr:splitStrS){
			//System.out.println(splitStr);
			String [] pathStrS=splitStr.split(",");
			String key=pathStrS[0]+"-"+pathStrS[1];	
			List<String> value=new ArrayList();
			value.add(pathStrS[2]);
			value.add(pathStrS[3]);
			value.add(pathStrS[4]); 
			trafficLightTable.put(key,value);
			//System.out.println(key+"  "+value[0]]);
		}
	}

	//从给定的车流量txt文件中读入所有的车流量信息
	public static void loadTrafficFlowTable(String fileName) throws IOException{
		BufferedReader readerTxt= new BufferedReader(new FileReader(fileName));
		String trafficFlow="";
		String lineStr="";
		//行扫描读入TrafficFlow
		while(lineStr!=null){
			if(lineStr!=""){
				trafficFlow+=lineStr+";";
			}
			lineStr=readerTxt.readLine();		
			//System.out.println(lineStr);
		}
		//字符串型的trafficFlow读入到HashMaP存储类型的trafficFlowTable
		String[] splitStrS=trafficFlow.split(";");
		for(String splitStr:splitStrS){
			String [] pathStrS = splitStr.split(",");
			String key=pathStrS[0]+"-"+pathStrS[1];	
			List value= new ArrayList();
			for(String pathStr:pathStrS){
				if(pathStr.equals(pathStrS[0])||pathStr.equals(pathStrS[1])){
					//System.out.print(pathStr+" ");
				}else{
					value.add(Integer.valueOf(pathStr));
					//System.out.print(Integer.valueOf(pathStr)+" ");
				}
			}
			//System.out.println();	 
			trafficFlowTable.put(key,value);
		}
	}

	//初始化红绿灯状态currentLightStatus，并将其保存到trafficLightStatus中，比赛时将由赛方给出
	private static void initTrafficLightStatus(){
		for(String key:trafficLightTable.keySet()){
			List<Integer> status=new ArrayList();
			status.add(0);
			status.add(0);
			status.add(0);
			if("#".equals(trafficLightTable.get(key).get(0))){
				status.set(0,-1);
			}else if("#".equals(trafficLightTable.get(key).get(1))){
				status.set(1,-1);
			}else if("#".equals(trafficLightTable.get(key).get(2))){
				status.set(2,-1);
			}
			currentLightStatus.put(key,status);
		}
		//System.out.println("currentLightStatus-size:"+currentLightStatus.size());
		
	}

	//检查红绿灯设置状态是否合法
	private static boolean isLegal(){
		
	}

}