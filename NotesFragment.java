
// واجهة المستخدم - 
package com.dua.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dua.app.R;
import com.dua.app.adapter.NoteAdapter;
import com.dua.app.viewmodel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;
    private NoteAdapter adapter;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);
        
        FloatingActionButton fab = rootView.findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putBoolean("new_note", true);
            Navigation.findNavController(v).navigate(R.id.action_notes_to_editor, args);
        });
        
        return rootView;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        notesViewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            adapter.submitList(notes);
        });
        
        adapter.setOnItemClickListener(note -> {
            Bundle args = new Bundle();
            args.putLong("note_id", note.getId());
            Navigation.findNavController(view).navigate(R.id.action_notes_to_editor, args);
        });
    }
}
