package algorithms.utils;


/** класс, который содержит градусы и значения этих градусов  */
public class Weight {
	
	/** возможный люфт для углов  */
	private static double[] orbis=new double[5];
	static{
		orbis[0]=30/60f;
		orbis[1]=1+ 30/60f;
		orbis[2]=2+45/60f;
		orbis[3]=4+15/60f;
		orbis[4]=5+45/60f;
	}
	/** <b>49</b> строк,<br> 
	 * <ul>
	 * 	<li><b>0</b> столбец - значение аспекта; </li>
	 * 	<li><b>1</b> - КПД орбис 30мин;  </li>
	 * 	<li><b>2</b> - КПД орбис 1 грд.30мин; </li>
	 * 	<li><b>3</b> - КПД орбис 2 грд.45мин;</li>
	 * 	<li><b>4</b> - КПД орбис 4 грд.15мин</li>
	 * 	<li><b>5</b> - КПД орбис 5 грд.45мин</li>
	 * </ul>
	 * 		   
	 * 
	 * */
	public static float[][] array=new float[49][7];
	static{
		 array[0][0]=0+0/60f;    array[0][1]=100;  array[0][2]=93;  array[0][3]=86; array[0][4]=79;  array[0][5]=70;	
		 array[1][0]=17+10/60f;  array[1][1]=61;   array[1][2]=0;   array[1][3]=0;  array[1][4]=0;   array[1][5]=0;		
		 array[2][0]=18+0/60f;   array[2][1]=61;   array[2][2]=0;   array[2][3]=0;  array[2][4]=0;   array[2][5]=0;		
		 array[3][0]=18+55/60f;  array[3][1]=61;   array[3][2]=0;   array[3][3]=0;  array[3][4]=0;   array[3][5]=0;		
		 array[4][0]=20+0/60f;   array[4][1]=62;   array[4][2]=0;   array[4][3]=0;  array[4][4]=0;   array[4][5]=0;		
		 array[5][0]=21+17/60f;  array[5][1]=62;   array[5][2]=0;   array[5][3]=0;  array[5][4]=0;   array[5][5]=0;		
		 array[6][0]=22+30/60f;  array[6][1]=62;   array[6][2]=0;   array[6][3]=0;  array[6][4]=0;   array[6][5]=0;		
		 array[7][0]=24+0/60f;   array[7][1]=63;   array[7][2]=60;  array[7][3]=0;  array[7][4]=0;   array[7][5]=0;		
		 array[8][0]=25+43/60f;  array[8][1]=63;   array[8][2]=0;   array[8][3]=0;  array[8][4]=0;   array[8][5]=0;		
		 array[9][0]=27+42/60f;  array[9][1]=63;   array[9][2]=60;  array[9][3]=0;  array[9][4]=0;   array[9][5]=0;		
		 array[10][0]=30+0/60f;  array[10][1]=64;  array[10][2]=60; array[10][3]=0; array[10][4]=0;  array[10][5]=0;	
		 array[11][0]=32+43/60f; array[11][1]=64;  array[11][2]=0;  array[11][3]=0; array[11][4]=0;  array[11][5]=0;	
		 array[12][0]=36+0/60f;  array[12][1]=64;  array[12][2]=60; array[12][3]=0; array[12][4]=0;  array[12][5]=0;	
		 array[13][0]=37+50/60f; array[13][1]=63;  array[13][2]=0;  array[13][3]=0; array[13][4]=0;  array[13][5]=0;	
		 array[14][0]=40+0/60f;  array[14][1]=64;  array[14][2]=60; array[14][3]=0; array[14][4]=0;  array[14][5]=0;	
		 array[15][0]=42+34/60f; array[15][1]=63;  array[15][2]=0;  array[15][3]=0; array[15][4]=0;  array[15][5]=0;	
		 array[16][0]=45+0/60f;  array[16][1]=64;  array[16][2]=60; array[16][3]=49;array[16][4]=0;  array[16][5]=0;	
		 array[17][0]=48+0/60f;  array[17][1]=63;  array[17][2]=0;  array[17][3]=0; array[17][4]=0;  array[17][5]=0;	
		 array[18][0]=51+25/60f; array[18][1]=94;  array[18][2]=87; array[18][3]=80;array[18][4]=0;  array[18][5]=0;	
		 array[19][0]=55+23/60f; array[19][1]=63;  array[19][2]=0;  array[19][3]=0; array[19][4]=0;  array[19][5]=0;	
		 array[20][0]=60+0/60f;  array[20][1]=95;  array[20][2]=88; array[20][3]=81;array[20][4]=0;  array[20][5]=0;	
		 array[21][0]=63+51/60f; array[21][1]=63;  array[21][2]=0;  array[21][3]=0; array[21][4]=0;  array[21][5]=0;	
		 array[22][0]=65+27/60f; array[22][1]=63;  array[22][2]=0;  array[22][3]=0; array[22][4]=0;  array[22][5]=0;	
		 array[23][0]=67+3/60f;  array[23][1]=63;  array[23][2]=0;  array[23][3]=0; array[23][4]=0;  array[23][5]=0;	
		 array[24][0]=72+0/60f;  array[24][1]=96;  array[24][2]=89; array[24][3]=82;array[24][4]=75; array[24][5]=0;	
		 array[25][0]=77+9/60f;  array[25][1]=65;  array[25][2]=0;  array[25][3]=0; array[25][4]=0;  array[25][5]=0;	
		 array[26][0]=80+0/60f;  array[26][1]=66;  array[26][2]=60; array[26][3]=49;array[26][4]=0;  array[26][5]=0;	
		 array[27][0]=83+05/60f; array[27][1]=65;  array[27][2]=0;  array[27][3]=0; array[27][4]=0;  array[27][5]=0;	
		 array[28][0]=90+0/60f;  array[28][1]=97;  array[28][2]=90; array[28][3]=83;array[28][4]=76; array[28][5]=71;	
		 array[29][0]=96+0/60f;  array[29][1]=66;  array[29][2]=0;  array[29][3]=0; array[29][4]=0;  array[29][5]=0;	
		 array[30][0]=98+11/60f; array[30][1]=66;  array[30][2]=0;  array[30][3]=0; array[30][4]=0;  array[30][5]=0;	
		 array[31][0]=102+50/60f;array[31][1]=67;  array[31][2]=60; array[31][3]=49;array[31][4]=0;  array[31][5]=0;	
		 array[32][0]=108+0/60f; array[32][1]=69;  array[32][2]=60; array[32][3]=0; array[32][4]=0;  array[32][5]=0;	
		 array[33][0]=110+46/60f;array[33][1]=67;  array[33][2]=0;  array[33][3]=0; array[33][4]=0;  array[33][5]=0;	
		 array[34][0]=112+30/60f;array[34][1]=68;  array[34][2]=0;  array[34][3]=0; array[34][4]=0;  array[34][5]=0;	
		 array[35][0]=120+0/60f; array[35][1]=98;  array[35][2]=91; array[35][3]=84;array[35][4]=77; array[35][5]=72;	
		 array[36][0]=128+34/60f;array[36][1]=65;  array[36][2]=0;  array[36][3]=0; array[36][4]=0;  array[36][5]=0;	
		 array[37][0]=130+55/60f;array[37][1]=65;  array[37][2]=0;  array[37][3]=0; array[37][4]=0;  array[37][5]=0;	
		 array[38][0]=135+0/60f; array[38][1]=69;  array[38][2]=60; array[38][3]=49;array[38][4]=0;  array[38][5]=0;	
		 array[39][0]=138+28/60f;array[39][1]=64;  array[39][2]=0;  array[39][3]=0; array[39][4]=0;  array[39][5]=0;	
		 array[40][0]=144+0/60f; array[40][1]=69;  array[40][2]=60; array[40][3]=0; array[40][4]=0;  array[40][5]=0;	
		 array[41][0]=150+0/60f; array[41][1]=69;  array[41][2]=60; array[41][3]=49;array[41][4]=0;  array[41][5]=0;	
		 array[42][0]=154+17/60f;array[42][1]=69;  array[42][2]=0;  array[42][3]=0; array[42][4]=0;  array[42][5]=0;	
		 array[43][0]=157+30/60f;array[43][1]=68;  array[43][2]=0;  array[43][3]=0; array[43][4]=0;  array[43][5]=0;	
		 array[44][0]=160+0/60f; array[44][1]=69;  array[44][2]=60; array[44][3]=49;array[44][4]=0;  array[44][5]=0;	
		 array[45][0]=163+38/60f;array[45][1]=65;  array[45][2]=0;  array[45][3]=0; array[45][4]=0;  array[45][5]=0;	
		 array[46][0]=166+9/60f; array[46][1]=64;  array[46][2]=0;  array[46][3]=0; array[46][4]=0;  array[46][5]=0;	
		 array[47][0]=168+0/60f; array[47][1]=63;  array[47][2]=0;  array[47][3]=0; array[47][4]=0;  array[47][5]=0;	
		 array[48][0]=180+0/60f; array[48][1]=99;  array[48][2]=92; array[48][3]=85;array[48][4]=78; array[48][5]=73;	
	}

	/** 0..100 вес аспекта  */
	public static int[] aspectWeight=new int[49];
	static{
		aspectWeight[0]=99;
		aspectWeight[1]=49;
		aspectWeight[2]=51;
		aspectWeight[3]=58;
		aspectWeight[4]=52;
		aspectWeight[5]=47;
		aspectWeight[6]=53;
		aspectWeight[7]=46;
		aspectWeight[8]=54;
		aspectWeight[9]=45;
		aspectWeight[10]=55;
		aspectWeight[11]=44;
		aspectWeight[12]=56;
		aspectWeight[13]=43;
		aspectWeight[14]=57;
		aspectWeight[15]=42;
		aspectWeight[16]=60;
		aspectWeight[17]=40;
		aspectWeight[18]=75;
		aspectWeight[19]=40;
		aspectWeight[20]=80;
		aspectWeight[21]=35;
		aspectWeight[22]=55;
		aspectWeight[23]=45;
		aspectWeight[24]=90;
		aspectWeight[25]=50;
		aspectWeight[26]=50;
		aspectWeight[27]=55;
		aspectWeight[28]=5;
		aspectWeight[29]=55;
		aspectWeight[30]=45;
		aspectWeight[31]=60;
		aspectWeight[32]=40;
		aspectWeight[33]=65;
		aspectWeight[34]=35;
		aspectWeight[35]=95;
		aspectWeight[36]=25;
		aspectWeight[37]=70;
		aspectWeight[38]=30;
		aspectWeight[39]=65;
		aspectWeight[40]=45;
		aspectWeight[41]=55;
		aspectWeight[42]=35;
		aspectWeight[43]=65;
		aspectWeight[44]=25;
		aspectWeight[45]=70;
		aspectWeight[46]=20;
		aspectWeight[47]=75;
		aspectWeight[48]=2;
	}
	
	/** 0..100 значение для указанного угла  */
	public static int[] risha=new int[49];
	static{
		risha[0]=90;
		risha[1]=10;
		risha[2]=50;
		risha[3]=90;
		risha[4]=10;
		risha[5]=50;
		risha[6]=90;
		risha[7]=10;
		risha[8]=50;
		risha[9]=90;
		risha[10]=10;
		risha[11]=50;
		risha[12]=90;
		risha[13]=90;
		risha[14]=10;
		risha[15]=50;
		risha[16]=50;
		risha[17]=10;
		risha[18]=90;
		risha[19]=90;
		risha[20]=10;
		risha[21]=50;
		risha[22]=50;
		risha[23]=90;
		risha[24]=50;
		risha[25]=50;
		risha[26]=10;
		risha[27]=90;
		risha[28]=90;
		risha[29]=10;
		risha[30]=50;
		risha[31]=90;
		risha[32]=90;
		risha[33]=90;
		risha[34]=90;
		risha[35]=10;
		risha[36]=50;
		risha[37]=50;
		risha[38]=50;
		risha[39]=90;
		risha[40]=50;
		risha[41]=10;
		risha[42]=90;
		risha[43]=90;
		risha[44]=10;
		risha[45]=50;
		risha[46]=90;
		risha[47]=10;
		risha[48]=50;
	}
	
	public static void main(String[] args){
		for(int counter=0;counter<array.length;counter++){
			System.out.println(counter+"  :  "+array[counter][0]);
		}
	}
	
	/** проверить, существует ли аспект между двумя углами ( то есть на временном отрезке были взяты два значения углов и попытка выяснить наличия между ними аспекта )  
	 * @param angle1 - угол первого шага 
	 * @param angle2 - угол второго шага 
	 * */
	public static boolean isAspectBetweenAngles(double angle1, double angle2){
		return getAspectBetween(angle1,angle2)!=null;
	}
	
	/** получить аспект между двумя углами   ( то есть на временном отрезке были взяты два значения углов и попытка выяснить наличия между ними аспекта )
	 * @param angle1 - угол первого шага 
	 * @param angle2 - угол второго шага
	 * @return 
	 * <ul>
	 * 	<li>int[] {<b>index of angle into array (row) (значение строки из {@link array} - номер строки  )</b>, 
	 * 			   <b>index of orbis into array (column) (значение колонки из {@link array}) кпд </b>}</li>
	 * 	<li>null - аспект не обнаружен по данному углу  </li>
	 * </ul>
	 * */
	public static int[] getAspectBetween(double angle1, double angle2){
		int[] returnValue=null;
		int[] currentValue=null;
		double a1=angle1;
		double a2=angle2;
		double angleBetween=0;
		if(angle1==angle2){
			returnValue=getAngleWeight(angle1);
		}else{
			if(a2<a1){
				a1=angle2;
				a2=angle1;
			}
			// a1<a2
			angleBetween=a2-a1;
			for(int counter=0;counter<array.length;counter++){
				currentValue=getWeigthForRange(counter, angleBetween);
				if(currentValue!=null){
					if(returnValue==null){
						// найден первый вариант 
						returnValue=currentValue;
					}else{
						// найден очередной варинат
						if(array[returnValue[0]][returnValue[1]] < array[currentValue[0]][currentValue[1]]){
							// найденный аспект имеет больший вес ( КПД )
							returnValue=currentValue;
						}else{
							// найденный аспект имеет меньший вес ( КПД )
						}
					}
				}else{
					// next
				}
			}
		}
		
		if(returnValue!=null){
			// проверка на наличие КПД по найденному аспекту
			if( array[returnValue[0]][returnValue[1]]>0 ){
				// есть КПД у аспекта 
			}else{
				// нет КПД у аспекта 
				returnValue=null;
			}
		}
		return returnValue;
	}
	
	/**
	 * получить ответ на наличие по указанному углу в таблице {@link Weight#array} соответствующего угла
	 * @param arrayIndex - индекс значения в массиве {@link #array} для анализа на принадлежность  
	 * @param angle - угол между планетами
	 * @return 
	 * <ul>
	 * 	<li>int[] {<b>index of angle into array (row)</b>, <b>index of orbis into array (column)</b>}</li>
	 * 	<li>null - аспект не обнаружен по данному углу  </li>
	 * </ul>
	 */
	private static int[] getWeigthForRange(int arrayIndex, 
										   double angle){
		for(int orbisCounter=0;orbisCounter<orbis.length;orbisCounter++){
			if (
				((Weight.array[arrayIndex][0]-Weight.orbis[orbisCounter])<angle)&&
			   ((Weight.array[arrayIndex][0]+Weight.orbis[orbisCounter])>angle)
			   ){
				return new int[]{arrayIndex, orbisCounter+1}; 
			}
		}
		return null;
		/*
		// проверка на полное попадание аспекта между двумя углами 
		if(( angle1<=array[arrayIndex][0]) &&(array[arrayIndex][0]<=angle2)){
			return new int[]{arrayIndex,1};
		}else{
			// проверка на попадание правой части аспекта к углу angle1
			for(int orbisCounter=0; orbisCounter<orbis.length; orbisCounter++){
				double value=array[arrayIndex][0]+orbis[orbisCounter];
				if((angle1<=value)&&(value<=angle2)){
					return new int[]{arrayIndex,orbisCounter+1};
				}
			}
			// проверка на попадание левой части аспекта к углу angle2 
			for(int orbisCounter=0; orbisCounter<orbis.length; orbisCounter++){
				double value=array[arrayIndex][0]-orbis[orbisCounter];
				if((angle1<=value)&&(value<=angle2)){
					return new int[]{arrayIndex,orbisCounter+1};
				}
			}
		}*/
	}
	
	/**  получить вес угла  - понять к какому аспекту примыкает, получить вес согласно значения 
	 * @param angle - значение угла - по которому нужно искать вес  
	 * @return 
	 * <ul>
	 * 	<li>int[] {<b>index of angle into array (row)</b>, <b>index of orbis into array (column)</b>}</li>
	 * 	<li>null - аспект не обнаружен по данному углу  </li>
	 * </ul>
	 * */
	public static int[] getAngleWeight(double angle){
		int[] returnValue=null;
		main_cycle: for(int orbisCounter=0;orbisCounter<orbis.length;orbisCounter++)
		for(int counter=0;counter<array.length;counter++){
			if( rangeInclude(angle, array[counter][0], orbis[orbisCounter])){
				returnValue=new int[]{counter, orbisCounter+1};
				break main_cycle;
			}
		}
		if(returnValue!=null){
			// проверка на наличие КПД по найденному аспекту
			if( array[returnValue[0]][returnValue[1]]>0 ){
				// есть КПД у аспекта 
			}else{
				// нет КПД у аспекта 
				returnValue=null;
			}
		}
		return returnValue;
	}

	/**  попадает ли указанный угол в радиус действия какого-либо аспекта  
	 * @param target - целевой угол
	 * @param controlValue - контрольное значение для этого угла 
	 * @param controlRadius - радиус контрольного значения  
	 * @return
	 */
	private static boolean rangeInclude(double target, double controlValue, double controlRadius){
		if(target==controlValue){
			return true;
		}else{
			if(target>controlValue){
				// цель больше контрольного значения
				return (controlValue+controlRadius)>=target;
			}else{
				// цель меньше контрольного значения 
				return target>=(controlValue-controlRadius);
			}
		}
	}
	
}
