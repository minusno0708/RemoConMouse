namespace SmartMouse
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
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
            Server.Server.Start();
        }
    }
}
