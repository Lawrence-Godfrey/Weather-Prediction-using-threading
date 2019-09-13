import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
public class CloudData {

	Vector[][][] advection; // in-plane regular grid of wind vectors, that evolve over time
	float[][][] convection; // vertical air movement strength, that evolves over time
	int[][][] classification; // cloud type per grid point, evolving over time
	int dimx, dimy, dimt; // data dimensions

	Vector averageWind = new Vector();

	// overall number of elements in the timeline grids
	int dim() {
		return dimt * dimx * dimy;
	}

	// convert linear position into 3D location in simulation grid
	void locate(int pos, int[] ind) {
		ind[0] = (int) pos / (dimx * dimy); // t
		ind[1] = (pos % (dimx * dimy)) / dimy; // x
		ind[2] = pos % (dimy); // y
	}

	void classify() {

		for (int t = 0; t < dimt; t++) {
			for (int x = 0; x < dimx; x++) {
				for (int y = 0; y < dimy; y++) {

					float avex=0;
					float avey=0;
					int dividor = 0;

					//go through all 8 surrounding elements
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							//if element is less than 0 or more than dimension ignore it
							if (!((x+i) < 0) && !((x+i) > dimx-1)) {
								if(!((y+j) < 0) && !((y+j) > dimy-1)){

										avex += advection[t][x + i][y + j].x;
										avey += advection[t][x + i][y + j].y;
										dividor++;
										//add to averages and keep track of number of elements added

								}
							}

						}
					}

					//calculate actual averages
					avex=avex/dividor;
					avey=avey/dividor;
                    //find magnitude
					double ave_magnitude = Math.sqrt(avex*avex + avey*avey);
					//add to classification using criteria
					if(Math.abs(convection[t][x][y])>ave_magnitude)
					{
						classification[t][x][y]=0;
					}
					else if(ave_magnitude>0.2 && (ave_magnitude >= Math.abs(convection[t][x][y])))
					{
						classification[t][x][y]=1;
					}
					else
					{
						classification[t][x][y]=2;
					}


					}
				}
			}
		}


	void findAve() {
		averageWind = new Vector();
		for (int t = 0; t < dimt; t++){
			for (int x = 0; x < dimx; x++){
				for (int y = 0; y < dimy; y++) {
					averageWind.x += advection[t][x][y].x;
					averageWind.y += advection[t][x][y].y;
				}
			}
		}
		averageWind.x = averageWind.x/dim();
		averageWind.y = averageWind.y/dim();
	}

	// read cloud simulation data from file
	void readData(String fileName){ 
		try{ 
			Scanner sc = new Scanner(new File(fileName), "UTF-8");
			
			// input grid dimensions and simulation duration in timesteps
			dimt = sc.nextInt();
			dimx = sc.nextInt(); 
			dimy = sc.nextInt();
			//System.out.println(dimt+" "+dimx + " "+dimy);
			// initialize and load advection (wind direction and strength) and convection
			advection = new Vector[dimt][dimx][dimy];
			convection = new float[dimt][dimx][dimy];
			for(int t = 0; t < dimt; t++)
				for(int x = 0; x < dimx; x++)
					for(int y = 0; y < dimy; y++){
						advection[t][x][y] = new Vector();
						advection[t][x][y].x = Float.parseFloat(sc.next());
						advection[t][x][y].y = Float.parseFloat(sc.next());
						convection[t][x][y] = Float.parseFloat(sc.next());
						//System.out.println(advection[t][x][y].x+" "+advection[t][x][y].y + " "+convection[t][x][y]);
					}
			
			classification = new int[dimt][dimx][dimy];
			sc.close(); 
		} 
		catch (IOException e){ 
			System.out.println("Unable to open input file "+fileName);
			e.printStackTrace();
		}
		catch (InputMismatchException e){ 
			System.out.println("Malformed input file "+fileName);
			e.printStackTrace();
		}
	}
	
	// write classification output to file
	void writeData(String fileName, Vector wind){
		 try{ 
			 FileWriter fileWriter = new FileWriter(fileName);
			 PrintWriter printWriter = new PrintWriter(fileWriter);
			 printWriter.printf("%d %d %d\n", dimt, dimx, dimy);
			 printWriter.printf(Locale.US,"%f %f\n", wind.x, wind.y);
			 for(int t = 0; t < dimt; t++){
				 for(int x = 0; x < dimx; x++){
					for(int y = 0; y < dimy; y++){
						printWriter.printf("%d ", classification[t][x][y]);
					}
				 }
				 printWriter.printf("\n");
		     }
				 
			 printWriter.close();
		 }
		 catch (IOException e){
			 System.out.println("Unable to open output file "+fileName);
				e.printStackTrace();
		 }
	}
}
