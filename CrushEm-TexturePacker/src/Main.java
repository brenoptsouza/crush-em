import com.badlogic.gdx.tools.imagepacker.TexturePacker2;


public class Main
{

	public static void main(String[] args)
	{
		TexturePacker2.process("C:\\Users\\Breno\\git\\CrushEm\\CrushEm-TexturePacker\\Game Textures\\CrushEm\\TexturePack\\pregame", "C:\\Users\\Breno\\git\\CrushEm\\CrushEm-android\\assets\\data", "menu_screen");
		TexturePacker2.process("C:\\Users\\Breno\\git\\CrushEm\\CrushEm-TexturePacker\\Game Textures\\CrushEm\\TexturePack\\game-screen", "C:\\Users\\Breno\\git\\CrushEm\\CrushEm-android\\assets\\data", "game_screen");
		TexturePacker2.process("C:\\Users\\Breno\\git\\CrushEm\\CrushEm-TexturePacker\\Game Textures\\CrushEm\\TexturePack\\loading-screen", "C:\\Users\\Breno\\git\\CrushEm\\CrushEm-android\\assets\\data", "loading_screen");
	}
	

}
