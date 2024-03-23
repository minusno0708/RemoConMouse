namespace Controller
{
    class Mouse
    {
        public static void Move(int moveX, int moveY)
        {
            int currentX = System.Windows.Forms.Cursor.Position.X;
            int currentY = System.Windows.Forms.Cursor.Position.Y;

            System.Windows.Forms.Cursor.Position = new System.Drawing.Point(currentX + moveX, currentY + moveY);
        }
    }
}