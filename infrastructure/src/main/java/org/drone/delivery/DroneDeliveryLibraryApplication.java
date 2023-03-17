    package org.drone.delivery;

    import org.drone.delivery.domain.DeliveryLocation;
    import org.drone.delivery.domain.Drone;
    import org.drone.delivery.parser.InputParser;
    import org.drone.delivery.usecase.DroneDeliveryService;

    import java.io.IOException;
    import java.util.List;

    public class DroneDeliveryLibraryApplication {
        public static void main(String[] args) throws IOException {
            String[] droneInputs = {"DroneA", "200", "DroneB", "250", "DroneC", "100"};
            String[] locationInputs = {
                    "LocationA", "200", "LocationB", "150", "LocationC", "50",
                    "LocationD", "150", "LocationE", "100", "LocationF", "200",
                    "LocationG", "50", "LocationH", "80", "LocationI", "70",
                    "LocationJ", "50", "LocationK", "30", "LocationL", "20",
                    "LocationM", "50", "LocationN", "30", "LocationO", "20",
                    "LocationP", "90"
            };

            InputParser inputParser = new InputParser();
            List<Drone> drones = inputParser.parseDrones(droneInputs);
            List<DeliveryLocation> locations = inputParser.parseDeliveryLocations(locationInputs);

            DroneDeliveryService service = new DroneDeliveryService();
            service.run(drones, locations);
        }

        private static void printDeliveries(List<String> list){
            for (String line : list) {
                System.out.println(line);
            }
        }

        //private static void printDeliveries(List<String> deliveries) {
//            int trip = 1;
//            for (Delivery delivery : deliveries) {
//                System.out.println("Trip #" + trip++);
//                List<String> locationNames = delivery.getLocations().stream()
//                        .map(location -> "[" + location.getName() + "]")
//                        .collect(Collectors.toList());
//                System.out.println(String.join(", ", locationNames));
//            }
        //}
    }
