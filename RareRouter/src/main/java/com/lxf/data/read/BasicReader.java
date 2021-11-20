package com.lxf.data.read;

interface BasicReader {
    void read(byte val_byte);

    void read(short val_short);

    void read(int val_int);

    void read(long val_long);

    void read(float val_float);

    void read(double val_double);

    void read(char val_char);

    void read(boolean val_bool);

    void read(String val_str);
}
