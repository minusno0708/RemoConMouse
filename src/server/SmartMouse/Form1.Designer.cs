namespace SmartMouse
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            serverLabel = new Label();
            serverStartButton = new Button();
            serverLog = new Label();
            ipLabel = new Label();
            ipList = new Label();
            SuspendLayout();
            // 
            // serverLabel
            // 
            serverLabel.AutoSize = true;
            serverLabel.Location = new Point(33, 23);
            serverLabel.Name = "serverLabel";
            serverLabel.Size = new Size(39, 15);
            serverLabel.TabIndex = 5;
            serverLabel.Text = "Server";
            // 
            // serverStartButton
            // 
            serverStartButton.Location = new Point(86, 19);
            serverStartButton.Name = "serverStartButton";
            serverStartButton.Size = new Size(75, 23);
            serverStartButton.TabIndex = 6;
            serverStartButton.Text = "Start";
            serverStartButton.UseVisualStyleBackColor = true;
            serverStartButton.Click += startButton_Click;
            // 
            // serverLog
            // 
            serverLog.AutoSize = true;
            serverLog.Location = new Point(33, 64);
            serverLog.Name = "serverLog";
            serverLog.Size = new Size(27, 15);
            serverLog.TabIndex = 7;
            serverLog.Text = "...";
            // 
            // ipLabel
            // 
            ipLabel.AutoSize = true;
            ipLabel.Location = new Point(33, 100);
            ipLabel.Name = "ipLabel";
            ipLabel.Size = new Size(17, 15);
            ipLabel.TabIndex = 10;
            ipLabel.Text = "IP";
            // 
            // ipList
            // 
            ipList.AutoSize = true;
            ipList.Location = new Point(70, 100);
            ipList.Name = "ipList";
            ipList.Size = new Size(40, 15);
            ipList.TabIndex = 11;
            ipList.Text = "0.0.0.0";
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(342, 160);
            Controls.Add(ipList);
            Controls.Add(ipLabel);
            Controls.Add(serverLog);
            Controls.Add(serverStartButton);
            Controls.Add(serverLabel);
            Name = "Form1";
            Text = "Smart Mouse";
            Load += Form1_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion
        private Label serverLabel;
        private Button serverStartButton;
        private Label serverLog;
        private Label ipLabel;
        private Label ipList;
    }
}
