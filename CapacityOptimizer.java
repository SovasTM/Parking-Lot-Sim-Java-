public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
		int capacityOfLot = 1;
		ParkingLot simulatedParkingLot = new ParkingLot(capacityOfLot);
		Simulator simulationOfParkingLot = new Simulator(simulatedParkingLot, hourlyRate, 24*3600);
		int totalOfSims = 0;
		int carsInQueue = 0;
		double averagePerSim = 0.0;
		boolean optimizedFound = false;

		while (!optimizedFound) {
			System.out.println("==== Setting lot capacity to: "+capacityOfLot+" ====");
			for (int i = 0; i < NUM_RUNS; i++) {
				simulationOfParkingLot.simulate();
				carsInQueue = simulationOfParkingLot.getIncomingQueueSize();
				System.out.println("Queue length at the end of the simulation run: "+carsInQueue);
				simulationOfParkingLot = new Simulator(simulatedParkingLot,hourlyRate,24*3600);
				totalOfSims += carsInQueue;
				carsInQueue = 0;
			}

			averagePerSim = totalOfSims / 10;

			if (averagePerSim <= 5) {
				optimizedFound = true;
			}
			else{
				capacityOfLot += 1;
				totalOfSims = 0;
				averagePerSim = 0.0;
				simulatedParkingLot = new ParkingLot(capacityOfLot);
				simulationOfParkingLot = new Simulator(simulatedParkingLot,hourlyRate,24*3600);
			}
		}
		return capacityOfLot;
	}
	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}