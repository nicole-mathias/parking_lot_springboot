

// public class ParkingLotDemo{

//     public static void main(String[] args){
//         ParkingLot lot = ParkingLot.getInstance();

//         lot.addFloor(new ParkingFloor(1, 5, 10, 2));
//         lot.addFloor(new ParkingFloor(2, 5, 10, 2));

//         Car car1 = new Car("ABC-123");
//         Car car2 = new Car("XYZ-789");
//         Bike bike = new Bike("BIKE-001");

//         ParkingTicket t1 = lot.parkVehicle(car1);
//         ParkingTicket t2 = lot.parkVehicle(car2);
//         ParkingTicket t3 = lot.parkVehicle(bike);

//         lot.displayAvailability();

//         double fee = lot.unParkVehicle(t1);

//         System.out.println("Fees: " + fee);

//         System.out.println("Select Payment method");    // setting card by default right now -- Startegy method
//         CardPayment payment_obj = new CardPayment(fee);
//         payment_obj.makePayment();

//         System.out.println("Car 1 paid: $" + fee);
//     }
// }

// // using strategy method for different hourly rates - weekend and weekday type
// // strategy method for payment - Card, cash