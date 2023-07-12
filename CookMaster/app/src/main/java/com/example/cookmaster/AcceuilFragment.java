package com.example.cookmaster;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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
        // Vérifier si le tag est compatible avec NDEF
        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            try {
                // Connectez-vous au tag NFC
                ndef.connect();

                // Lisez les enregistrements NDEF du tag
                NdefMessage ndefMessage = ndef.getNdefMessage();
                NdefRecord[] records = ndefMessage.getRecords();

                // Parcourez tous les enregistrements et récupérez le texte
                for (NdefRecord record : records) {
                    if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                        byte[] payload = record.getPayload();
                        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
                        int languageCodeLength = payload[0] & 0063;
                        String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
                        return text;
                    }
                }
            } catch (IOException | FormatException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Déconnectez-vous du tag NFC
                    ndef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Si aucune donnée n'a été lue ou si le tag n'est pas au format NDEF,
        // vous pouvez retourner un texte par défaut ou un message indiquant l'absence de données
        return "Bravo vous avez gagné un bon de reduction de -5% ( code: cinq ) ";
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
