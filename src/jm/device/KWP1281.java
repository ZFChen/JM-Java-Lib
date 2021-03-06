package jm.device;

public abstract class KWP1281 extends KLineProtocol {

    public final byte FrameEnd = 0x03;
    protected int _frameCounter = 0;

    @Override
    public byte[] pack(byte[] data) throws IllegalArgumentException {
        if (data == null || data.length <= 0) {
            throw new IllegalArgumentException();
        }

        byte[] ret = new byte[3 + data.length];
        ret[0] = (byte)(data.length + 20);
        ret[1] = frameCounterIncrement();
        System.arraycopy(data, 0, ret, 2, data.length);
        ret[data.length + 2] = FrameEnd;
        return ret;
    }

    @Override
    public byte[] unpack(byte[] data) throws IllegalArgumentException {
        if (data == null || (data.length - 2 <= 0)) {
            throw new IllegalArgumentException();
        }

        byte[] ret = new byte[data.length - 2];
        System.arraycopy(data, 1, ret, 0, data.length - 2);
        return ret;
    }

    protected byte frameCounterIncrement() {
        _frameCounter = (_frameCounter + 1) & 0xFF;
        return (byte)(_frameCounter);
    }
}
