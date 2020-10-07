package qlsv;

import java.io.File;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;

public class DB {

	// khai bao ten CSDL
	private final String DB_FILE = "qlsv.db4o";

	// khai bao object container chua CSDL
	public ObjectContainer container = null;

	public DB() {
		this.openDB();
	}

	// phuong thuc mo CSDL
	public void openDB() {
		final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		this.container = Db4oEmbedded.openFile(config, this.DB_FILE);
	}

	// phuong thuc dong CSDL
	public void closeDB() {
		this.container.close();
	}

	// phuong thuc xoa CSDL
	public void deleteDB() {
		this.closeDB();
		this.container = null;
		new File(this.DB_FILE).delete();
	}

	public void begin() {
		// db4o tu dong bat dau giao tac - transaction
	}

	public void commit() {
		this.container.commit();
	}

	public void rollback() {
		this.container.rollback();
	}

	// luu tru doi tuong vao CSDL
	public void store(final Object obj) {
		this.container.store(obj);
	}

	// luu tru doi tuong vao CSDL
	public void delete(final Object obj) {
		this.container.delete(obj);
	}

	// thuc thi truy van du lieu
	public Query query() {
		return this.container.query();
	}

	// dua ra danh sach doi tuong dai dien cua lop
	public <T> List<T> getByClass(final Class<?> obj) {
		final Query query = this.container.query();
		query.constrain(obj);
		return this.execute(query);
	}

	// dua ra danh sach ket qua cua truy van
	public <T> List<T> execute(final Query query) {
		return query.execute();
	}

	protected void finalize() {
		closeDB();
	}
}
