const nodemailer = require('nodemailer');

const smtpUsername = process.env.SMTP_USERNAME;
const smtpPassword = process.env.SMTP_PASSWORD;
const prNumber     = process.env.NUMBER_PR;
const tag_name     = process.env.TAG_NAME;
const emailsToSend = process.env.SEND_EMAILS.split(',');

const transporter = nodemailer.createTransport({
  host: 'smtp.gmail.com',
  port: 465,
  secure: true,
  auth: {
    user: smtpUsername,
    pass: smtpPassword,
  },
});

const mailOptions = {
  from: smtpUsername,
  to: emailsToSend,
  subject: 'Aprovação da release ' + tag_name + ' para PRODUÇÃO',
  text: 'Para que a nova release vá para produção, é necessário a sua aprovação nesse link\n\nLink do PR: https://github.com/Filmaro/trabalho-edsII/pull/' + prNumber + '\n\n*Após atingir o número mínimo de aprovações, a release será gerada automáticamente.',
};

transporter.sendMail(mailOptions, (error, info) => {
  if (error) {
    console.error('Erro ao enviar o e-mail:', error);
  } else {
    console.log('E-mail enviado com sucesso:', info.response);
  }
});