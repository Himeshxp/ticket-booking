package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserBookingService {
    private User user;
    private static String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    ObjectMapper objectMapper = new ObjectMapper();


    private List<User> userList;

    public UserBookingService() throws IOException{
        loadUserprofileFile();
    }

    private void loadUserprofileFile() throws IOException {
        File file = new File(USER_FILE_PATH);

        // ensure directory exists
        file.getParentFile().mkdirs();

        // first run or empty file
        if (!file.exists() || file.length() == 0) {
            userList = new ArrayList<>();
            objectMapper.writeValue(file, userList);
            return;
        }

        userList = objectMapper.readValue(
                file,
                new TypeReference<List<User>>() {}
        );
    }


    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserprofileFile();

    }

    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName())
                    && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public void fetchBookings() {
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName())
                    && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if (userFetched.isPresent()) {
            userFetched.get().printTickets();

        }

    }
//public void fetchBookings() {
//    Optional<User> userFetched = userList.stream()
//            .filter(u -> u.getName().equalsIgnoreCase(user.getName())
//                    && UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword()))
//            .findFirst();
//
//    if (userFetched.isEmpty()) {
//        System.out.println("No user found. Please login again.");
//        return;
//    }
//
//    User loggedInUser = userFetched.get();
//
//    if (loggedInUser.getTicketsBooked() == null || loggedInUser.getTicketsBooked().isEmpty()) {
//        System.out.println("No bookings found.");
//        return;
//    }
//
//    loggedInUser.printTickets();
//}


    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainservice = new TrainService();
            return trainservice.searchTrains(source, destination);
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try {
            TrainService trainservice = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row > 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainservice.addTrain(train);
                    return true;
                } else {
                    return false;
                }}else {
                    return false; // Invalid row or seat index
                }

        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }
//public Boolean bookTrainSeat(Train train, int row, int seat) {
//    try {
//        TrainService trainService = new TrainService();
//        List<List<Integer>> seats = train.getSeats();
//
//        // validate indices
//        if (row < 0 || row >= seats.size() || seat < 0 || seat >= seats.get(row).size()) {
//            return false;
//        }
//
//        // seat already booked
//        if (seats.get(row).get(seat) == 1) {
//            return false;
//        }
//
//        // 1. book seat in train
//        seats.get(row).set(seat, 1);
//        train.setSeats(seats);
//        trainService.addTrain(train);
//
//        // 2. find logged-in user from userList
//        Optional<User> userFetched = userList.stream()
//                .filter(u -> u.getName().equalsIgnoreCase(user.getName())
//                        && UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword()))
//                .findFirst();
//
//        if (userFetched.isEmpty()) {
//            return false;
//        }
//
//        User loggedInUser = userFetched.get();
//
//        // 3. create ticket object (using existing Ticket class)
//        Ticket ticket = new Ticket(
//                UUID.randomUUID().toString(),
//                loggedInUser.getUserId(),
//                "source",        // you can improve this later
//                "destination",   // you can improve this later
//                "date",          // you can improve this later
//                train
//        );
//
//        // 4. attach ticket to user
//        loggedInUser.getTicketsBooked().add(ticket);
//
//        // 5. save users.json
//        saveUserListToFile();
//
//        return true;
//
//    } catch (IOException ex) {
//        ex.printStackTrace();
//        return false;
//    }
//}

}

