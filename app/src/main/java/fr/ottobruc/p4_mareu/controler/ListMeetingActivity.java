package fr.ottobruc.p4_mareu.controler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import fr.ottobruc.p4_mareu.R;
import fr.ottobruc.p4_mareu.databinding.ActivityListmeetingBinding;
import fr.ottobruc.p4_mareu.di.DI;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.repository.MeetingRepository;

public class ListMeetingActivity extends AppCompatActivity {
    private ActivityListmeetingBinding binding;
    private MeetingRepository meetingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListmeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        meetingRepository = DI.getMeetingRepository();
    }