package snippet;

public class Snippet {
	public HomeView() {
	    try {
	        ConnectDB.getinstance().connect();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    setLayout(new BorderLayout());
	    ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/homeView.jpg"));
	    lblBackground = new JLabel(imageIcon);
	    add(lblBackground, BorderLayout.CENTER);
	}
	
}

