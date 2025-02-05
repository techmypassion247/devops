package com.app.milkbook;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothHelper {
    private static final String TAG = "BluetoothHelper";
    private static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard Bluetooth UUID for printers

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private Context context;

    public BluetoothHelper(String printerAddress) {
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Check if the device supports Bluetooth
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the Bluetooth device by address
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(printerAddress);
    }

    public BluetoothHelper(String printerAddress, ReportActivity reportActivity) {
    }

    public void connect() throws IOException {
        if (bluetoothDevice == null) {
            Toast.makeText(context, "Printer not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cancel discovery as it can slow down the connection
        bluetoothAdapter.cancelDiscovery();

        try {
            // Create the Bluetooth socket and connect to the device
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(PRINTER_UUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            Toast.makeText(context, "Connected to printer", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to printer: " + e.getMessage());
            close();
            throw e;
        }
    }

    public void printData(String data) throws IOException {
        if (outputStream == null) {
            throw new IOException("Printer not connected");
        }

        try {
            outputStream.write(data.getBytes());
            outputStream.flush();
            Toast.makeText(context, "Data sent to printer", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Error printing data: " + e.getMessage());
            throw e;
        }
    }

    public void close() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing Bluetooth connection: " + e.getMessage());
        }
    }

    public static Set<BluetoothDevice> getPairedDevices() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter.getBondedDevices();
    }
}
