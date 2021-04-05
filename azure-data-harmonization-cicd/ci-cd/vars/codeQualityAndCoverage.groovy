def call(Map config = ['srcPmdPath': '', 
				'srcCheckstylePath': '', 
				'testClassPath': '', 
				'testSrcPath': '',
				'findBugsPath': '', 
				'junitPath': '']){
				    
				    
/*				'testPmdPath': '',
				'testCheckstylePath': '', 
*/

    parallel (
       'Findbugs': {
            step([$class: 'FindBugsPublisher', 
                canComputeNew: false, 
                defaultEncoding: '', 
                excludePattern: '', 
                healthy: '', 
                includePattern: '', 
                pattern: "$config.findBugsPath", 
                unHealthy: ''])
        },

        'PMD': {
            step([$class: 'PmdPublisher', 
                canComputeNew: false, 
                defaultEncoding: '', 
                healthy: '', 
                pattern: "$config.srcPmdPath", 
                unHealthy: ''])
        },
                    
/*        'PMD_TEST': {
            step([$class: 'PmdPublisher', 
                canComputeNew: false, 
                defaultEncoding: '', 
                healthy: '', 
                pattern: "$config.testPmdPath", 
                unHealthy: ''])
        },*/

        'CHECKSTYLE': {
            step([$class: 'CheckStylePublisher',
				canRunOnFailed: true,
				defaultEncoding: '',
				healthy: '',
				pattern: "$config.srcCheckstylePath", 
				unHealthy: '',
				useStableBuildAsReference: true])
        },

/*        'CHECKSTYLE_TEST': {
            step([$class: 'CheckStylePublisher',
				canRunOnFailed: true,
				defaultEncoding: '',
				healthy: '',
				pattern: "$config.testCheckstylePath", 
				unHealthy: '',
				useStableBuildAsReference: true])
        },*/

		'jacoco': {
		//	jacoco classPattern: "$config.testClassPath", sourcePattern: "$config.testSrcPath"
		step( [ $class: 'JacocoPublisher' ] )
        },

		'junit': {
			junit allowEmptyResults: true, testResults: "$config.junitPath"
        }
	)

}
