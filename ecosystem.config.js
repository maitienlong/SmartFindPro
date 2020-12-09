module.exports = {
  apps : [{
    script: 'index.js',
    watch: false,
    aurestart: true
  }
  ],

  deploy : {
    production : {
      user : 'root',
      host: 'localhost',
      ref  : 'origin/server',
      repo : 'git@github.com:maitienlong/SmartFindPro.git',
      path : '~/SmartFindPro',
      'pre-deploy-local': '',
      'post-deploy' : 'npm install && pm2 reload ecosystem.config.js --env production',
      'pre-setup': ''
    }
  }
};
