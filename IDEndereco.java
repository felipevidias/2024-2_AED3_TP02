
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IDEndereco implements RegistroHashExtensivel<IDEndereco> {

    private int id; // key
    private long endereco; // value

    private final short TAMANHO = 12;

    public IDEndereco() {
        this.id = -1;
        this.endereco = -1;
    }

    public IDEndereco(int id, long end) {
        this.id = id;
        this.endereco = end;
    }

    public int getId() {
        return id;
    }

    public long getEndereco() {
        return endereco;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.id + ";" + this.endereco + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeLong(this.endereco);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.endereco = dis.readLong();
    }

}
