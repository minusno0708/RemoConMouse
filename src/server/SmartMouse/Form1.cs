using System.ComponentModel;

namespace SmartMouse
{
    public partial class Form1 : Form
    {
        private static Form1? instance;

        public Form1()
        {
            InitializeComponent();
            instance = this;
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            int inputX, inputY;

            try
            {
                inputX = int.Parse(textBox1.Text);
                inputY = int.Parse(textBox2.Text);
            }
            catch
            {
                inputX = 0;
                inputY = 0;
            }
            Controller.Mouse.Move(inputX, inputY);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Server.Start();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Server.Send(textBox3.Text);
        }

        public static void updateLog(string newLogText)
        {
            if (instance != null)
            {
                instance.label4.Text = newLogText;   
            }
        }
    }
}
