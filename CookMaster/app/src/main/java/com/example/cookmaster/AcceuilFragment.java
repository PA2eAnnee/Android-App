package com.example.cookmaster;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class AcceuilFragment extends Fragment {

    private static final int NFC_PERMISSION_REQUEST_CODE = 100;
    private static final long READ_TIMEOUT = 10000; // 10 seconds

    private Button btnNFC;
    private NfcAdapter nfcAdapter;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        View view = inflater.inflate(R.layout.fragment_acceuil, container, false);
        btnNFC = view.findViewById(R.id.btnNFC);

        // Check if NFC is available on the device
        NfcManager nfcManager = (NfcManager) requireActivity().getSystemService(Context.NFC_SERVICE);
        if (nfcManager != null) {
            nfcAdapter = nfcManager.getDefaultAdapter();
        }

        btnNFC.setOnClickListener(v -> enableNFC());

        return view;
    }

    private void enableNFC() {
        // Check NFC permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.NFC}, NFC_PERMISSION_REQUEST_CODE);
            return;
        }

        if (nfcAdapter == null || !nfcAdapter.isEnabled()) {
            Toast.makeText(requireContext(), "NFC is not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show a Toast instructing the user to place the NFC token on the phone
        Toast.makeText(requireContext(), "Place the NFC token on the phone", Toast.LENGTH_SHORT).show();

        // Enable NFC reading
        nfcAdapter.enableReaderMode(requireActivity(), new NfcAdapter.ReaderCallback() {
            @Override
            public void onTagDiscovered(Tag tag) {
                // Read the NFC tag
                String tagData = readTagData(tag);

                // Show the tag data in a Toast
                showToast(tagData);
            }
        }, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null);

        // Set a timeout to stop NFC reading after 10 seconds
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                disableNFC();
            }
        }, READ_TIMEOUT);

        Toast.makeText(requireContext(), "NFC enabled. Please scan a tag.", Toast.LENGTH_SHORT).show();
    }


    private void disableNFC() {
        if (nfcAdapter != null) {
            nfcAdapter.disableReaderMode(requireActivity());
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        Toast.makeText(requireContext(), "NFC disabled.", Toast.LENGTH_SHORT).show();
    }

    private String readTagData(Tag tag) {
        // TODO: Implement your NFC tag reading logic here
        // For example, you can read the tag ID
        byte[] tagId = tag.getId();
        return ByteArrayToHexString(tagId);
    }

    private void showToast(final String message) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String ByteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NFC_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableNFC();
            } else {
                Toast.makeText(requireContext(), "NFC permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
