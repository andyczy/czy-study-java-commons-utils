package field;


import Book.Book;

//StringTest Class
public class Test {
	public static void main(String[] args) {
		Book bo = new Book();
		bo.setNo(1);
		bo.setName("斗破苍穹");
		bo.setType("玄幻");
		
		ShowField ft = new ShowField();
	    ft.show(Book.class);
		ft.obshow(bo);
	}
}




