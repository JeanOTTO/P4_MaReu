package fr.ottobruc.p4_mareu.controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import fr.ottobruc.p4_mareu.databinding.ActivityListmeetingBinding;
import fr.ottobruc.p4_mareu.di.DI;
import fr.ottobruc.p4_mareu.model.Meeting;
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
    }

    public void onClickDelete(Meeting meeting) {
        Log.d(ListMeetingActivity.class.getName(), "User tries to delete a item.");
        meetingRepository.deleteMeeting(meeting);
        Toast.makeText(this, "Réunion supprimée !", Toast.LENGTH_SHORT).show();
        initList();
    }

}