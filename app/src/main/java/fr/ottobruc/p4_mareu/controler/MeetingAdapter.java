package fr.ottobruc.p4_mareu.controler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import fr.ottobruc.p4_mareu.databinding.MeetingItemBinding;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.User;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {
    private List<Meeting> meetings;
    private final Listener callback;
    public interface Listener {
        void onClickDelete(Meeting meeting);
    }

    public MeetingAdapter(List<Meeting> meetings, Listener callback) {
        this.meetings = meetings;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MeetingItemBinding binding = MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MeetingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.bind(meeting);
        holder.binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onClickDelete(meeting);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private MeetingItemBinding binding;

        public MeetingViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Meeting meeting) {
            // Affiche l'heure de la réunion
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String time = timeFormat.format(meeting.getStartTime());

            // Mettre à jour les vues avec les données de la réunion
            binding.colorIndicatorView.setColorFilter(meeting.getLocation().getColor());
            binding.subjectTextView.setText(meeting.getSubject()+" - "+time+" - "+meeting.getLocation().getName());

            // Concaténer les e-mails des participants
            StringBuilder participantsBuilder = new StringBuilder();
            for (User participant : meeting.getParticipants()) {
                participantsBuilder.append(participant.getEmail()).append(", ");
            }

            // Supprimer la virgule et l'espace à la fin
            if (participantsBuilder.length() > 2) {
                participantsBuilder.setLength(participantsBuilder.length() - 2);
            }
            binding.participantsTextView.setText(participantsBuilder.toString());
        }
    }
    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
