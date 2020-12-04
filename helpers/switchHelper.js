import {helper} from '@ember/component/helper';

function substring(args) {
    let [string, start, end] = args;
    return string.substring(start, end);
}

export default helper(substring);