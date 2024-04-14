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
            if (instance != null)
            {
                instance.ipList.Text = Server.getIP();
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void startButton_Click(object sender, EventArgs e)
        {
            Server.Start();
        }

        private void stopButton_Click(object sender, EventArgs e)
        {
            Server.Stop();
        }

        public static void updateLog(string newLogText)
        {
            if (instance != null)
            {
                instance.serverLog.Text = newLogText;   
            }
        }
    }
}
