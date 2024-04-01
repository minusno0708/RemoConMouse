
using System.Runtime.InteropServices;
using System.Threading;

namespace Controller
{
    class Mouse
    {
        [DllImport("user32.dll")]
        private static extern void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);

        private enum MouseEventFlags
        {
            LeftDown = 0x02,
            LeftUp = 0x04
        }


        public static void Move(int moveX, int moveY)
        {
            int currentX = Cursor.Position.X;
            int currentY = Cursor.Position.Y;

            Cursor.Position = new System.Drawing.Point(currentX + moveX, currentY + moveY);
        }

        public static void Click(string button)
        {
            if (button == "left")
            {
                mouse_event((int)MouseEventFlags.LeftDown, 0, 0, 0, 0);
                Thread.Sleep(100);
                mouse_event((int)MouseEventFlags.LeftUp, 0, 0, 0, 0);
            }

        }
    }
}