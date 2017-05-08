package cn.mldn.util.orm;
/**
 * 由于在设置PreparedStatement接口对象内容的时候要通过VO类的getter()方法
 * 取得对应数据，所以应该有一个类完成此数据的取得操作。以及对应的类型的操作项
 *
 */
public class FieldTypeAndValue {
	private String name ;	// 属性名字
	private String type ;	// 属性的类型
	private Object value ; 	// 属性内容
	public FieldTypeAndValue() {}
	public FieldTypeAndValue(String name, String type, Object value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "FieldTypeAndValue [name=" + name + ", type=" + type + ", value="
				+ value + "]";
	}
}
