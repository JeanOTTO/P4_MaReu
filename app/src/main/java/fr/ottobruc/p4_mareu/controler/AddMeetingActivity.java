package fr.ottobruc.p4_mareu.controler;

import static fr.ottobruc.p4_mareu.utils.DateTimeUtil.isTimeConflict;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ottobruc.p4_mareu.R;
import fr.ottobruc.p4_mareu.databinding.ActivityAddMeetingBinding;
import fr.ottobruc.p4_mareu.di.DI;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.model.User;
import fr.ottobruc.p4_mareu.repository.MeetingRepository;

/**
 * This activity allows users to add a new meeting by selecting a date, time, room, and participants.
 */
public class AddMeetingActivity extends AppCompatActivity {
    private ActivityAddMeetingBinding binding;
    private MeetingRepository meetingRepository;
    private List<Room> availableRooms;
    private List<String> participantEmails;
    private Calendar calendar;
    private Date selectedDate;
    private Date selectedStartTime;
    private Date selectedEndTime;
    private Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        meetingRepository = DI.getMeetingRepository();
        availableRooms = new ArrayList<>(meetingRepository.getRooms());
        participantEmails = new ArrayList<>();
        calendar = Calendar.getInstance();
        setupDatePicker();
        setupTimePicker();
        setupRoomSpinner();
        setupEmailAutoComplete();
        setupAddParticipantButton();
        setupCreateMeetingButton();
    }

    /**
     * Sets up the date picker dialog and handles the selection of the meeting date.
     */
    private void setupDatePicker() {
        binding.dateEditText.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    selectedDate = updateDate(cal, "date");
                    if (selectedStartTime != null) {
                        cal.setTime(selectedStartTime);
                        selectedStartTime = updateDate(cal, "time");
                    }
                    if (selectedEndTime != null) {
                        cal.setTime(selectedEndTime);
                        selectedEndTime = updateDate(cal, "time");
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    binding.dateEditText.setText(dateFormat.format(selectedDate));
                    try {
                        updateRoomSpinner();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
            datePickerDialog.show();
        });
    }

    /**
     * Updates the selected date in the calendar based on the provided type.
     *
     * @param cal  the Calendar instance to update
     * @param type the type of update ("date" or "time")
     * @return the updated Date object
     */
    private Date updateDate(Calendar cal, String type) {
        switch (type) {
            case "date":
                calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
                break;
            case "time":
                calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
                calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
                break;
        }
        return calendar.getTime();
    }

    /**
     * Opens a time picker dialog and handles the selection of the meeting start and end times.
     */
    private void selectTime(String startEnd) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute1) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute1);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                if (startEnd.equals("start")) {
                    selectedStartTime = updateDate(cal, "time");
                    binding.startTimeEditText.setText(timeFormat.format(selectedStartTime));
                } else if (startEnd.equals("end")) {
                    selectedEndTime = updateDate(cal, "time");
                    binding.endTimeEditText.setText(timeFormat.format(selectedEndTime));
                }
                if (selectedEndTime == null || selectedEndTime.before(selectedStartTime)) {
                    cal.setTime(selectedStartTime);
                    cal.add(Calendar.MINUTE, 45);
                    selectedEndTime = cal.getTime();
                    binding.endTimeEditText.setText(timeFormat.format(selectedEndTime));
                }
                try {
                    updateRoomSpinner();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    /**
     * Sets up the time picker dialog and handles the selection of the meeting start and end times.
     */
    private void setupTimePicker() {
        binding.startTimeEditText.setOnClickListener(v -> {
            selectTime("start");
        });
        binding.endTimeEditText.setOnClickListener(v -> {
            selectTime("end");
        });
    }

    /**
     * Sets up the email auto-complete functionality for the participant input field.
     */
    private void setupEmailAutoComplete() {
        List<String> listEmails = new ArrayList<>();
        for (User user : meetingRepository.getUsers()) {
            listEmails.add(user.getEmail());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, listEmails);
        binding.participantAutoCompleteTextview.setThreshold(0);
        binding.participantAutoCompleteTextview.setAdapter(adapter);
    }

    /**
     * Sets up the room selection spinner.
     */
    private void setupRoomSpinner() {
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, availableRooms);
        binding.roomAutoCompleteTextview.setAdapter(adapter);
        binding.roomAutoCompleteTextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoom = availableRooms.get(i);
            }
        });
    }

    /**
     * Sets up the functionality of the "Add Participant" button.
     */
    private void setupAddParticipantButton() {
        binding.addParticipantButton.setOnClickListener(v -> {
            String email = binding.participantAutoCompleteTextview.getText().toString().trim();
            if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                Toast.makeText(this, R.string.err_add_emailformat, Toast.LENGTH_SHORT).show();
                return;
            }
            if (participantEmails.contains(email)) {
                Toast.makeText(this, R.string.err_add_userAlreadyAdded, Toast.LENGTH_SHORT).show();
                return;
            }
            participantEmails.add(email);
            binding.participantAutoCompleteTextview.setText("");
            updateParticipantList();
        });
    }

    /**
     * Sets up the functionality of the "Create Meeting" button.
     */
    private void setupCreateMeetingButton() {
        binding.createMeetingButton.setOnClickListener(v -> {
            if (!validateInput()) {
                return;
            }
            String subject = binding.subjectEditText.getText().toString().trim();
            Meeting newMeeting = new Meeting(
                    0, // Assign a unique ID or generate one
                    selectedStartTime,
                    selectedEndTime,
                    selectedDate,
                    selectedRoom,
                    subject,
                    getSelectedParticipants()
            );
            try {
                if (isRoomAvailable(newMeeting)) {
                    meetingRepository.addMeeting(newMeeting);
                    Toast.makeText(this, R.string.add_meetingcreate, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, R.string.err_add_booked, Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Validates the user input for creating a meeting.
     *
     * @return true if the input is valid, false otherwise
     */
    private boolean validateInput() {
        boolean result;
        result = false;
        if ((binding.subjectEditText.getText().toString()).isEmpty()) {
            binding.subjectEditText.setError(getResources().getString(R.string.err_add_subject));
        }
        if (selectedDate == null) {
            binding.dateEditText.setError(getResources().getString(R.string.err_add_date));
        }
        if (selectedStartTime == null) {
            binding.startTimeEditText.setError(getResources().getString(R.string.err_add_starttime));
        }
        if (selectedEndTime == null) {
            binding.endTimeEditText.setError(getResources().getString(R.string.err_add_starttime));
        }
        if (selectedRoom == null) {
            binding.roomAutoCompleteTextview.setError(getResources().getString(R.string.err_add_endtime));
        }
        if (participantEmails.isEmpty()) {
            binding.participantAutoCompleteTextview.setError(getResources().getString(R.string.err_add_participant));
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Retrieves the selected participants for the meeting.
     *
     * @return a list of User objects representing the selected participants
     */
    private List<User> getSelectedParticipants() {
        List<User> participants = new ArrayList<>();
        for (String email : participantEmails) {
            participants.add(new User(email));
        }
        return participants;
    }

    /**
     * Updates the participant list view with the selected participants.
     */
    private void updateParticipantList() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        String job;
        for (String email : participantEmails) {
            i++;
            job = "";
            for (User user : meetingRepository.getUsers()) {
                if (user.getEmail().equals(email)) {
                    job = " (" + user.getJob() + ")";
                }
            }
            builder.append(i).append(":  ").append(email).append(job).append("\n");
        }
        binding.participantListTextView.setText(builder.toString());
    }

    /**
     * Validates if the given email is in a valid format.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Retrieves the list of available rooms based on the selected date and time.
     *
     * @return a list of available Room objects
     * @throws ParseException if there is an error parsing the date or time
     */
    private List<Room> getAvailableRooms() throws ParseException {
        List<Room> filteredRooms = new ArrayList<>(meetingRepository.getRooms());
        for (Meeting meeting : meetingRepository.getMeetings()) {
            if (isTimeConflict(selectedStartTime, selectedEndTime, meeting.getStartTime(), meeting.getEndTime())) {
                filteredRooms.remove(meeting.getLocation());
            }
        }
        return filteredRooms;
    }

    /**
     * Updates the room spinner with the list of available rooms based on the selected date and time.
     *
     * @throws ParseException if there is an error parsing the date or time
     */
    private void updateRoomSpinner() throws ParseException {
        if (selectedStartTime != null && selectedDate != null) {
            ArrayAdapter<Room> roomAdapter = (ArrayAdapter<Room>) binding.roomAutoCompleteTextview.getAdapter();
            roomAdapter.clear();
            availableRooms = getAvailableRooms();
            roomAdapter.addAll(availableRooms);
            roomAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Navigates to the AddMeetingActivity.
     *
     * @param activity the FragmentActivity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * Checks if the selected room is available for the meeting.
     *
     * @param newMeeting the new Meeting object to be checked
     * @return true if the room is available, false otherwise
     * @throws ParseException if there is an error parsing the date or time
     */
    private boolean isRoomAvailable(Meeting newMeeting) throws ParseException {
        for (Meeting existingMeeting : meetingRepository.getMeetings()) {
            if (existingMeeting.getLocation().equals(newMeeting.getLocation()) &&
                    isTimeConflict(selectedStartTime, selectedEndTime, existingMeeting.getStartTime(), existingMeeting.getEndTime())) {
                return false;
            }
        }
        return true;
    }
}
