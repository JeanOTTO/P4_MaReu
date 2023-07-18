package fr.ottobruc.p4_mareu.controler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.ottobruc.p4_mareu.R;
import fr.ottobruc.p4_mareu.databinding.ActivityListmeetingBinding;
import fr.ottobruc.p4_mareu.di.DI;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.Room;
import fr.ottobruc.p4_mareu.repository.MeetingRepository;

public class ListMeetingActivity extends AppCompatActivity implements MeetingAdapter.Listener {
    private ActivityListmeetingBinding binding;
    private MeetingRepository meetingRepository;
    private List<Meeting> meetings;
    private MeetingAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListmeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        meetingRepository = DI.getMeetingRepository();
        initList();
    }
    public void initList() {
        meetings = meetingRepository.getMeetings();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.meetingRecyclerView.setLayoutManager(layoutManager);
        adapter = new MeetingAdapter(meetings, this);
        binding.meetingRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        binding.meetingRecyclerView.setAdapter(adapter);
        binding.createMeeting.setOnClickListener(v -> AddMeetingActivity.navigate(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    public void onClickDelete(Meeting meeting) {
        Log.d(ListMeetingActivity.class.getName(), "User tries to delete a item.");
        meetingRepository.deleteMeeting(meeting);
        Toast.makeText(this, "Réunion supprimée !", Toast.LENGTH_SHORT).show();
        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter_off) {
            // Action de filtrage par date
            initList();
            return true;
        }
        if (id == R.id.action_filter_date) {
            // Action de filtrage par date
            showDatePickerDialog();
            return true;

        } else if (id == R.id.action_filter_room) {
            // Action de filtrage par salle
            showRoomFilterDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog() {
        // Obtenez la date actuelle
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Créez un sélecteur de date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Mettez à jour la date sélectionnée
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
            Date selectedDate = selectedCalendar.getTime();

            // Filtrez les réunions en fonction de la date sélectionnée
            List<Meeting> filteredMeetings = meetingRepository.getFilteredMeetingsByDate(selectedDate);

            // Mettez à jour l'adaptateur de la liste des réunions avec les réunions filtrées
            adapter.setMeetings(filteredMeetings);
            adapter.notifyDataSetChanged();
        }, year, month, day);

        // Affichez le sélecteur de date
        datePickerDialog.show();
    }

    private void showRoomFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrer par salle");

        // Créez un tableau de noms de salle à partir de la liste des salles disponibles
        String[] roomNames = new String[meetingRepository.getRooms().size()];
        for (int i = 0; i < meetingRepository.getRooms().size(); i++) {
            roomNames[i] = meetingRepository.getRooms().get(i).getName();
        }

        // Tableau pour stocker les salles sélectionnées
        boolean[] checkedItems = new boolean[meetingRepository.getRooms().size()];

        builder.setMultiChoiceItems(roomNames, checkedItems, (dialog, which, isChecked) -> checkedItems[which] = isChecked);

        builder.setPositiveButton("Filtrer", (dialog, which) -> {
            List<Meeting> filteredMeetings = filterMeetingsByRoom(checkedItems);

            adapter.setMeetings(filteredMeetings);
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Annuler", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<Meeting> filterMeetingsByRoom(boolean[] checkedItems) {
        List<Meeting> filteredMeetings;
        filteredMeetings = new ArrayList<>();
        for (int i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i]) {
                Room room = meetingRepository.getRooms().get(i);
                filteredMeetings.addAll(meetingRepository.getFilteredMeetingsByRoom(room));
            }
        }
        return filteredMeetings;
    }

}