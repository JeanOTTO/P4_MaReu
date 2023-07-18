package fr.ottobruc.p4_mareu.controler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import fr.ottobruc.p4_mareu.R;
import fr.ottobruc.p4_mareu.databinding.MeetingItemBinding;
import fr.ottobruc.p4_mareu.model.Meeting;
import fr.ottobruc.p4_mareu.model.User;

/**
 * Adapter class for RecyclerView, used to display a list of meetings.
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {
    private List<Meeting> meetings;
    private final Listener callback;

    /**
     * Interface to handle delete click events.
     */
    public interface Listener {
        void onClickDelete(Meeting meeting);
    }

    /**
     * Constructor for the MeetingAdapter class.
     * @param meetings The list of meetings to display
     * @param callback The listener for delete click events
     */
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
        holder.binding.itemView.setContentDescription(meeting.getSubject());
        holder.binding.deleteButton.setContentDescription("Supprimer la réunion "+ meeting.getSubject());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    /**
     * ViewHolder class for the RecyclerView items.
     */
    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        private MeetingItemBinding binding;

        public MeetingViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds a meeting to the ViewHolder.
         * @param meeting The meeting to bind
         */
        public void bind(Meeting meeting) {
            // Display the meeting time
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String time = timeFormat.format(meeting.getStartTime());

            // Update the views with the meeting data
            binding.colorIndicatorView.setColorFilter(meeting.getLocation().getColor());
            binding.subjectTextView.setText(meeting.getSubject()+" - "+time+" - "+meeting.getLocation().getName());

            // Concatenate the participant emails
            StringBuilder participantsBuilder = new StringBuilder();
            for (User participant : meeting.getParticipants()) {
                participantsBuilder.append(participant.getEmail()).append(", ");
            }

            // Remove the trailing comma and space
            if (participantsBuilder.length() > 2) {
                participantsBuilder.setLength(participantsBuilder.length() - 2);
            }
            binding.participantsTextView.setText(participantsBuilder.toString());
        }
    }

    /**
     * Sets the meetings to display in the RecyclerView.
     * @param meetings The meetings to display
     */
    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
