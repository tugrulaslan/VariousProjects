package trader;

import java.io.Serializable;

public class Customer implements Serializable {

    private String id;
    private String name;
    private String addr;

    // Constructors
    public Customer(String id, String name, String addr) {
        this.id = id;
        this.name = name;
        this.addr = addr;
    }

    public Customer(String id) {
        this(id, null, null);
    }

    public Customer() {
        this(null, null, null);
    }

    // Accesser methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    // Mutator methods - note you cannot change the id
    public void setName(String newName) {
        name = newName;
    }

    public void setAddr(String newAddr) {
        addr = newAddr;
    }

    public String toString() {
        return "Customer:  " + id + "  " + name + "  " + addr;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
}

